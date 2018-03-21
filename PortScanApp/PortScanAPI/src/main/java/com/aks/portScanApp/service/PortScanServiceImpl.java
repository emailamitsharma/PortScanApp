/**
 * 
 */
package com.aks.portScanApp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.aks.portScanApp.excception.InvalidDataProcessingException;
import com.aks.portScanApp.model.ChangeInState;
import com.aks.portScanApp.model.NMapCommand;
import com.aks.portScanApp.model.NMapData;
import com.aks.portScanApp.model.PortModel;
import com.aks.portScanApp.model.PortState;
import com.aks.portScanApp.model.ScanDetails;
import com.aks.portScanApp.repositories.NMapDataRepository;
import com.aks.portScanApp.repositories.ScanDetailsRepository;
import com.aks.portScanApp.util.PortScanAppConstants;
import com.aks.portScanApp.util.PortScanUtil;

/**
 * @author r56529
 *
 */
@Service
public class PortScanServiceImpl implements PortScanService {

	private final static Logger logger = LoggerFactory.getLogger(PortScanServiceImpl.class); 

	@Autowired
	private NMapDataRepository portDataRepository;

	@Autowired
	private ScanDetailsRepository scanRepository;

	@Autowired
	private CommandExecutor nmapExecutorService;

	@Autowired
	private CommandOutputProcessingService nmapProcessingService;

	@Value("${nmap.command.template}")
	private String nmapCommandTemplate;

	@Value("${nmap.exec.mock:false}")
	private boolean isNmapExecutionMock;

	@Value("${nmap.exec.mockValue}")
	private String mockNMapValue;

	@Value("${scan.history.upto:1}")
	private int scanHistoryMaxItr;


	@Override
	public List<NMapData> findNMapDataByPreviousScan(ScanDetails lastScan){

		return portDataRepository.findByscanDetail(lastScan).orElse(null);
	}


	@Override
	public List<PortModel> findPreviousScanPortsForHostId(String hostId) throws Exception {

		ScanDetails lastScan = scanRepository.findFirstByIdentifierOrderByScanIdDesc(hostId);

		List<NMapData> 	lastScanPorts= findNMapDataByPreviousScan(lastScan);

		return lastScanPorts!= null?PortScanUtil.transformDataToModel(lastScanPorts):null;

	}

	@SuppressWarnings({})
	@Override
	@Transactional(readOnly=false)
	public List<PortModel> processForPortScanning(String hostId, boolean isLatestScanRequired) throws Exception {

		try {
			if(isLatestScanRequired){
				logger.debug("Processing request with latest scanning for host " + hostId);
				ScanDetails latestScanDetail = new ScanDetails();
				latestScanDetail.setIdentifier(hostId);

				ScanDetails lastScan = scanRepository.findFirstByIdentifierOrderByScanIdDesc(hostId);

				List<NMapData> previousNmapData = null;

				if(lastScan != null)
					previousNmapData = new ArrayList<>(lastScan.getNmapData());//lazy loading of data 

				List<NMapData> latestNmapData = processRequestForNMapScan(latestScanDetail);

				saveResultedRecords(latestScanDetail, latestNmapData);

				List<PortModel> finalMergedList = mergePortsAsPerChangeInState(latestNmapData, previousNmapData,latestScanDetail,lastScan);

				List<NMapData> pullPreviousHistory=null;

				if(lastScan!=null && scanHistoryMaxItr!=0){
					pullPreviousHistory  = scanHistoryMaxItr == -1 ?
							portDataRepository.findPreviousScansAllHistory(hostId,lastScan.getScanId()):
								portDataRepository.findPreviousScansPortsUptoIteration(hostId,lastScan.getScanId(),scanHistoryMaxItr);

							if(!CollectionUtils.isEmpty(pullPreviousHistory))
								finalMergedList.addAll(PortScanUtil.transformDataToModel(pullPreviousHistory));

				}

				return finalMergedList;

			}else{
				logger.debug("Processing request without latest scanning for host " + hostId);
				List<PortModel> lastScanPorts = findPreviousScanPortsForHostId(hostId);
				return lastScanPorts;
			}
		} catch (Exception e) {
			logger.error(String.format("Error while processing web request for %s with error | %s |", hostId,e.getMessage()));
			throw e;
		}
	}





	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void saveResultedRecords(ScanDetails scanDetail, List<NMapData> nmapRecordsList) {
		if(scanDetail!= null && scanDetail.getScanId() == null){//new scan is required and hence need to entry in database
			scanDetail.setCreatedBy(PortScanAppConstants.SYSTEM_USER);
			scanDetail.setScanTimeStamp(LocalDateTime.now());
			scanDetail.setCompleted(true);
			scanDetail.setNmapData(new HashSet(nmapRecordsList));
			nmapRecordsList.forEach(nmapRecord -> nmapRecord.setScanDetail(scanDetail));
			scanRepository.save(scanDetail);
		}
	}


	private List<NMapData> processRequestForNMapScan(ScanDetails scanDetail) throws Exception {

		if(scanDetail!= null && scanDetail.getIdentifier()==null)
			throw new InvalidDataProcessingException();

		NMapCommand anNmapCommand = new NMapCommand(nmapCommandTemplate, scanDetail.getIdentifier());

		String nmapCommandExecResult = null;
		
		if(!isNmapExecutionMock)//because could not installed it on my company laptop due to admin issues
			nmapCommandExecResult = nmapExecutorService.execute(anNmapCommand);
		else
			nmapCommandExecResult=mockNMapValue;

		logger.info(String.format("NMAP scanning results for hostId %s with command %s  is :  %s ", scanDetail.getIdentifier(),anNmapCommand.getCommand(),nmapCommandExecResult));
		
		if(nmapCommandExecResult == null || nmapCommandExecResult.trim().length()==0)
			return null;
		
		List<NMapData> nmapRecordsList = nmapProcessingService.parseNMapResponse(nmapCommandExecResult);


		return nmapRecordsList;
	}



	private List<PortModel> mergePortsAsPerChangeInState(List<NMapData> latestScanList,List<NMapData> previousScanList,ScanDetails latestScan, ScanDetails previousScan){

		if(latestScanList == null && previousScanList == null)
			return null;

		//do not initialize as it is merging case with minimum as 0 and max as size1 + size2. 
		List<PortModel> finalList = new ArrayList<>();

		//First iterate list of fresh scanning to find for latest added ports + already retained in scan too.
		iterateLatestListForAdditionAndCarryCases(latestScanList, previousScanList, finalList,latestScan);


		//Now iterate on previous scanned list to add subtraction cases.Find out which were there before and not now.
		iteratePreviousListForSubstractionCases(latestScanList, previousScanList, finalList,previousScan);

		return finalList;
	}

	private void iterateLatestListForAdditionAndCarryCases(List<NMapData> latestScanList,
			List<NMapData> previousScanList, List<PortModel> finalList,ScanDetails latestScan) {
		if (!CollectionUtils.isEmpty(latestScanList)) {
			latestScanList.stream().forEach(lastestScanPort -> {
				Optional<NMapData> previousRecord = findPortInList(previousScanList, lastestScanPort);

				if (previousRecord.isPresent()) {//if existing port.

					PortModel aPortModel = new PortModel();
					aPortModel.setScanTime(latestScan.getScanTimeStamp());
					aPortModel.setHostId(latestScan.getIdentifier());
					PortScanUtil.copyNmapDataToNmapModel(lastestScanPort, aPortModel);

					if (!lastestScanPort.getPortState().equals(previousRecord.get().getPortState()))
						aPortModel.setIsStateChanged(ChangeInState.YES);
					else
						aPortModel.setIsStateChanged(ChangeInState.NO);


					if (!(lastestScanPort.getPortState().equals(PortState.CLOSED) //only add when port is open , or was open or was close ,else leave it (in case of close & close).
							&& previousRecord.get().getPortState().equals(PortState.CLOSED)))
						finalList.add(aPortModel);

				} else {
					if (lastestScanPort.getPortState().equals(PortState.OPEN)) {//only add when port is open
						PortModel aPortModel = new PortModel();
						aPortModel.setScanTime(latestScan.getScanTimeStamp());
						aPortModel.setHostId(latestScan.getIdentifier());
						aPortModel.setIsStateChanged(CollectionUtils.isEmpty(previousScanList)? ChangeInState.NA: ChangeInState.YES);

						PortScanUtil.copyNmapDataToNmapModel(lastestScanPort, aPortModel);

						finalList.add(aPortModel);
					}
				}
			});
		}
	}

	private void iteratePreviousListForSubstractionCases(List<NMapData> latestScanList,
			List<NMapData> previousScanList, List<PortModel> finalList,ScanDetails previousScan) {
		//Now iterate on previous scanned list to add subtraction cases.Find out which were there before and not now.
		if(!CollectionUtils.isEmpty(previousScanList)){

			List<NMapData> filterMinusRecords = previousScanList.stream().filter(previousScanRecord -> latestScanList == null || !latestScanList.contains(previousScanRecord)).collect(Collectors.toList());

			filterMinusRecords.forEach(previousRecord -> {

				if(previousRecord.getPortState().equals(PortState.OPEN)){//only add when previous state was OPEN ,otherwise do not add.
					PortModel aPortModel = new PortModel();

					aPortModel.setScanTime(previousScan.getScanTimeStamp());

					aPortModel.setIsStateChanged(ChangeInState.YES);

					aPortModel.setHostId(previousScan.getIdentifier());

					PortScanUtil.copyNmapDataToNmapModel(previousRecord, aPortModel);

					finalList.add(aPortModel);

				}

			});

		}
	}


	private Optional<NMapData> findPortInList(List<NMapData> portList,final NMapData singlePort){
		return portList != null ? portList.stream().filter(previousScanPort -> previousScanPort.equals(singlePort)).findFirst() : Optional.empty();
	}

}
