/**
 * 
 */
package com.aks.portScanApp.model;

import java.time.LocalDateTime;

import com.aks.portScanApp.util.CustomJsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Amit.Sharma
 *
 */
public class PortModel {
	
	private int portNumber;
	
	private PortState portState;
	
	@JsonSerialize(using=CustomJsonDateTimeSerializer.class)
	private LocalDateTime scanTime;
	
	private ChangeInState isStateChanged;
	
	private String serviceUsedFor;
	
	private String hostId;

	public int getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}

	public PortState getPortState() {
		return portState;
	}

	public void setPortState(PortState portState) {
		this.portState = portState;
	}

	public LocalDateTime getScanTime() {
		return scanTime;
	}

	public void setScanTime(LocalDateTime scanTime) {
		this.scanTime = scanTime;
	}

	public String getServiceUsedFor() {
		return serviceUsedFor;
	}

	public void setServiceUsedFor(String serviceUsedFor) {
		this.serviceUsedFor = serviceUsedFor;
	}

	public ChangeInState getIsStateChanged() {
		return isStateChanged;
	}

	public void setIsStateChanged(ChangeInState isStateChanged) {
		this.isStateChanged = isStateChanged;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		PortModel nmapData = (PortModel) o;

		if (Integer.compare(nmapData.portNumber, portNumber) != 0) return false;		
		return true;
	}

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}
	
}
