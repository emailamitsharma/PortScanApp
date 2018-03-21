/**
 * 
 */
package com.aks.portScanApp.model;

/**
 * @author Amit.sharma
 *
 */
public enum ChangeInState {

	YES,NO,NA;
	
	private String actualChange;
	
	ChangeInState(){
		
	}

	ChangeInState(String actualChange) {
		this.actualChange = actualChange;
	}

	public String getActualChange() {
		return actualChange;
	}
	
	public void setActualChange(String actualChange) {
		this.actualChange = actualChange;
	}
	
}
