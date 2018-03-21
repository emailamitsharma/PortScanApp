/**
 * 
 */
package com.aks.portScanApp.service;

import java.util.List;
import java.util.concurrent.Callable;

import com.aks.portScanApp.model.PortModel;

/**
 * @author r56529
 *
 */
public class PortScanConcurrentRequestAdaptor implements Callable<List<PortModel>>{
	
	private String hostId;
	
	private boolean isLatestScanRequired;
	
	private PortScanService portScanService;

	
	public PortScanConcurrentRequestAdaptor(String hostId, boolean isLatestScanRequired,
			PortScanService portScanService) {
		super();
		this.hostId = hostId;
		this.isLatestScanRequired = isLatestScanRequired;
		this.portScanService = portScanService;
	}

	
	@Override
	public List<PortModel> call() throws Exception {
		
		return portScanService.processForPortScanning(hostId, isLatestScanRequired);
	}

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public boolean isLatestScanRequired() {
		return isLatestScanRequired;
	}

	public void setLatestScanRequired(boolean isLatestScanRequired) {
		this.isLatestScanRequired = isLatestScanRequired;
	}

}
