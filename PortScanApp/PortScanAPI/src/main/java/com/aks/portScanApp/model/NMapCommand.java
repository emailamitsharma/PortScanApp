/**
 * 
 */
package com.aks.portScanApp.model;

import java.util.Arrays;

/**
 * @author Amit.sharma
 * Specific to run NMap command
 */

public class NMapCommand extends BaseCommand{


	
	private String[] cmdScript;

	public NMapCommand(String nmapCommand,String hostName) {
		super(nmapCommand);
		String [] scriptCommand = {"/bin/sh", "-c",formatCommand(hostName)};
		setCmdScript(scriptCommand);
	}

	private String formatCommand(String hostName){
		return String.format(getCommand(), hostName);
	}

	public String[] getCmdScript() {
		return cmdScript;
	}

	public void setCmdScript(String[] cmdScript) {
		this.cmdScript = cmdScript;
	}
	
	@Override
	public String toString() {
		return Arrays.asList(cmdScript).toString();
	}
}
