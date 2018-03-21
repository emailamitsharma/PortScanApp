package com.aks.portScanApp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.aks.portScanApp.excception.BadRequestException;
import com.aks.portScanApp.model.PortModel;



@Service("portScanService")
@Transactional
public class PortScanRequestProcessorImpl implements PortScanRequestProcessor{

	public static final Logger logger = LoggerFactory.getLogger(PortScanRequestProcessorImpl.class);

	@Autowired
	private PortScanService portScanService;

	@Value("${scan.concurrency.required:false}")
	private boolean isConcurrentProcessingRequired;

	@Autowired
	private ThreadPoolTaskExecutor concurrentProcessor;


	@SuppressWarnings({})
	@Override
	@Transactional(readOnly=false)
	public List<PortModel> processRequest(String[] hostIds, boolean isLatestScanRequired) throws Exception {

		try {

			if(hostIds == null || hostIds.length==0)
				throw new BadRequestException();

			List<PortModel> finalProcessedList = new ArrayList<>();

			if(isConcurrentProcessingRequired){
				logger.debug(String.format("Processing request in parallel for hostId %s with latest scanning ", hostIds,isLatestScanRequired));
				
				processRecordsInParallel(hostIds, isLatestScanRequired, finalProcessedList);
			}else{
				logger.debug(String.format("Processing request in sequence for hostId %s with latest scanning ", hostIds,isLatestScanRequired));
				
				processRecordsInSequence(hostIds, isLatestScanRequired, finalProcessedList);
			}

			return finalProcessedList;
		} catch (Exception e) {
			logger.error(String.format("Error while processing web request for %s with error | %s |", hostIds,e.getMessage()));
			throw e;
		}
	}


	private void processRecordsInSequence(String[] hostIds, boolean isLatestScanRequired,
			List<PortModel> finalProcessedList) throws Exception {
		for(String host:hostIds){

			List<PortModel> processedListForHost = portScanService.processForPortScanning(host, isLatestScanRequired);

			if(!CollectionUtils.isEmpty(processedListForHost))
				finalProcessedList.addAll(processedListForHost);
		}
	}


	private void processRecordsInParallel(String[] hostIds, boolean isLatestScanRequired,
			List<PortModel> finalProcessedList) throws Exception{
		List<Future<List<PortModel>>> allFutureCalls = new ArrayList<>();
		
		for(String host:hostIds){

			PortScanConcurrentRequestAdaptor anAdaptor = new PortScanConcurrentRequestAdaptor(host, isLatestScanRequired, portScanService);

			Future<List<PortModel>> processedListForHost = concurrentProcessor.submit(anAdaptor);

			allFutureCalls.add(processedListForHost);
		}

		List<Exception> allExceptions = new ArrayList<>(); 

		allFutureCalls.stream().forEach(aFuture -> {

			try {
				List<PortModel> processedListForHost = aFuture.get();

				if(processedListForHost!= null)
					finalProcessedList.addAll(processedListForHost);
			} catch (InterruptedException | ExecutionException e ) {				
				allExceptions.add(e);
			}	

		});
		
		if(!CollectionUtils.isEmpty(allExceptions)){
			allExceptions.stream().forEach(e -> logger.error("Error while processing records for given host" + e.getMessage()));
			throw allExceptions.get(0);
		}
	
	}
}