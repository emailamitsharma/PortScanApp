/**
 * 
 */
package com.aks.portScanApp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aks.portScanApp.excception.InvalidDataProcessingException;
import com.aks.portScanApp.model.NMapData;
import com.aks.portScanApp.model.PortState;
import com.aks.portScanApp.util.PortScanAppConstants;

/**
 * @author Amit.sharma
 *
 */
@Service
public class NMapOutputProcessingServiceImpl implements CommandOutputProcessingService{

	@Override
	public List<NMapData> parseNMapResponse(String output) throws Exception {
		if(output == null || output.length()==0)
			throw new InvalidDataProcessingException();


		String[] listOfLines = output.split(PortScanAppConstants.NEW_LINE);

		if(listOfLines == null || listOfLines.length<1)
			return null;

		List<NMapData> refData = new ArrayList<>(listOfLines.length);

		Arrays.stream(listOfLines).forEach(line -> {
			String[] str = line.trim().split(PortScanAppConstants.FORWARD_SLASH);

			if(str== null || str.length==0)
				return;//only exit this iteration , not whole loop

			NMapData aPortInfo = new NMapData();
			aPortInfo.setPortNumber(Integer.parseInt(str[0]));
			aPortInfo.setPortState(PortState.valueOf(str[1].toUpperCase()));
			aPortInfo.setServiceUsedFor(str[4]);

			refData.add(aPortInfo);

		});
		return refData;
	}

}
