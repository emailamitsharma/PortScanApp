/**
 * 
 */
package com.aks.portScanApp.service;

import java.util.List;

import com.aks.portScanApp.model.NMapData;
import com.aks.portScanApp.model.PortModel;
import com.aks.portScanApp.model.ScanDetails;

/**
 * @author r56529
 *
 */
public interface PortScanService {

	List<NMapData> findNMapDataByPreviousScan(ScanDetails scanDetail); 
	
	public List<PortModel> processForPortScanning(String hostId,boolean isLatestScanRequired) throws Exception;

	public List<PortModel> findPreviousScanPortsForHostId(String hostId) throws Exception;

}
