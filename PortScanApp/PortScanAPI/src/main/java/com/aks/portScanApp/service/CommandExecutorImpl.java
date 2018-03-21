/**
 * 
 */
package com.aks.portScanApp.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.aks.portScanApp.excception.InvalidDataProcessingException;
import com.aks.portScanApp.model.BaseCommand;
import com.aks.portScanApp.model.NMapCommand;
import com.aks.portScanApp.util.PortScanAppConstants;

/**
 * @author Amit.sharma
 *
 */
@Service
public class CommandExecutorImpl implements CommandExecutor{

	public static final Logger logger = LoggerFactory.getLogger(CommandExecutorImpl.class);

	@Override
	public String execute(BaseCommand nmapCommand) throws Exception {

		
		logger.debug(String.format("Command received for execution is %s", nmapCommand));

		
		StringBuffer output = new StringBuffer();

		Process p;
		try {
			p = Runtime.getRuntime().exec(((NMapCommand)nmapCommand).getCmdScript());
			p.waitFor();

			BufferedReader reader = 
					new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = PortScanAppConstants.BLANK_STRING;	

			while ((line = reader.readLine())!= null) {
				output.append(line + PortScanAppConstants.NEW_LINE);
			}

		} catch (Exception e) {
			logger.error(String.format("Error while executing the nmap command for  %s with error as | %s |" , nmapCommand.getCommand(),e.getMessage()));
			throw e;
		}

		return output.toString();

	}

}
