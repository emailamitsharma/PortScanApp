package com.aks.portScanApp.service;

import java.util.List;

import com.aks.portScanApp.model.PortModel;

public interface PortScanRequestProcessor {

	public List<PortModel> processRequest(String[] hostIds,boolean isLatestScanRequired) throws Exception;
	
	
}