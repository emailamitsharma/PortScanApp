/**
 * 
 */
package com.aks.portScanApp.service;

import java.util.List;

import com.aks.portScanApp.model.NMapData;

/**
 * @author Amit.sharma
 *
 */
public interface CommandOutputProcessingService {

	public List<NMapData> parseNMapResponse(String output) throws Exception;
}
