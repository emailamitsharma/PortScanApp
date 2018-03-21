/**
 * 
 */
package com.aks.portScanApp.model;

/**
 * @author Amit.sharma
 *
 */
public class BaseCommand {

	private String command;

	public BaseCommand(String anyCommand) {
		this.command = anyCommand;
	}

	public String getCommand() 
	{
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}
	
	@Override
	public String toString() {
		
		return "[ Command is : " + command +  " ]";
	}
}
