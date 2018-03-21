package com.aks.portScanApp.controller;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.validator.routines.DomainValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aks.portScanApp.excception.BadRequestException;
import com.aks.portScanApp.excception.NoDataFoundException;
import com.aks.portScanApp.model.PortModel;
import com.aks.portScanApp.service.PortScanRequestProcessor;
import com.aks.portScanApp.util.PortScanAppConstants;

@RestController
@RequestMapping(value="/api", produces={MediaType.APPLICATION_JSON_VALUE})
public class PortScanController {

	public static final Logger logger = LoggerFactory.getLogger(PortScanController.class);

	@Autowired
	private PortScanRequestProcessor portScanService;

	@RequestMapping(value = "/ports", method = RequestMethod.GET)
	public ResponseEntity<List<PortModel>> listAllPorts(@RequestParam(required=true,value="input") String inputValue,@RequestParam(required=false, value="isLatestScanRequired") boolean isLatestScanRequired) throws Exception{		
		logger.debug("Received request for listing ports for identifier " + inputValue);
		
		if(inputValue == null || inputValue.length()<1){
			throw new BadRequestException();//Client side error in request having in-sufficient data.
		}
				
		//support for multiple records
		String[] allHosts = inputValue.split(PortScanAppConstants.COMMA_STRING);
	
		boolean isAnyInputValueViolation = Arrays.stream(allHosts).anyMatch(inputHost -> 
																!PortScanAppConstants.IP_ADD_PATTERN.matcher(inputHost).matches()
															 && !DomainValidator.getInstance().isValid(inputHost));
			
		
		if(isAnyInputValueViolation)
			throw new BadRequestException();

		List<PortModel> portlist = portScanService.processRequest(allHosts, isLatestScanRequired);
		
		if(CollectionUtils.isEmpty(portlist)){
			throw new NoDataFoundException();
		}
		
		return new ResponseEntity<List<PortModel>>(portlist, HttpStatus.OK);
	}

}