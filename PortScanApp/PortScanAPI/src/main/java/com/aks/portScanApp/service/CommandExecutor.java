/**
 * 
 */
package com.aks.portScanApp.service;

import com.aks.portScanApp.model.BaseCommand;

/**
 * @author Amit.sharma
 *
 */
public interface CommandExecutor {

	public String execute(BaseCommand anyCommand) throws Exception;
}
