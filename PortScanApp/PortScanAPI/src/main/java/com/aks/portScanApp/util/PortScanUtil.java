/**
 * 
 */
package com.aks.portScanApp.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.aks.portScanApp.model.ChangeInState;
import com.aks.portScanApp.model.NMapData;
import com.aks.portScanApp.model.PortModel;

/**
 * @author Amit.sharma
 * This class works as utility for this application. All generic operations, sorting, transformation can be taken place here to provide segregation, re-usability & efficiency as static utilities. 
 *
 */
public class PortScanUtil {

	public static void copyNmapDataToNmapModel(NMapData data,PortModel model){
		model.setPortNumber(data.getPortNumber());
		model.setPortState(data.getPortState());
		model.setServiceUsedFor(data.getServiceUsedFor());
	}

	public static List<PortModel> transformDataToModel(List<NMapData> nmapRecordsList){
		
		if(CollectionUtils.isEmpty(nmapRecordsList))
			return null;
		List<PortModel> transformedList = new ArrayList<>(nmapRecordsList.size());//initialize with it's size to avoid memory wastage

		nmapRecordsList.stream().forEach(data -> {

			PortModel aModel = new PortModel();
			aModel.setScanTime(data.getScanDetail().getScanTimeStamp());
			aModel.setHostId(data.getScanDetail().getIdentifier());
			aModel.setIsStateChanged(ChangeInState.NA);
			copyNmapDataToNmapModel(data, aModel);
			transformedList.add(aModel);
		});
		return transformedList;
	}
}
