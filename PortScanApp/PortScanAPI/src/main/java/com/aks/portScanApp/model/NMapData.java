/**
 * 
 */
package com.aks.portScanApp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Amit.Sharma
 *
 */
@Entity
@Table(name="port_details")
public class NMapData extends PersistenceData {


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name="port_number")
	private Integer portNumber;

	@Column(name="state")
	@Enumerated(EnumType.STRING)
	private PortState portState;
	
	@Column(name="service")
	private String serviceUsedFor;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="scan_id",nullable=false)
	private ScanDetails scanDetail;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		NMapData nmapData = (NMapData) o;

		if (Integer.compare(nmapData.portNumber, portNumber) != 0) return false;		
		return true;
	}

	@Override
	public int hashCode() {
		int result;
		long temp=1;
		result = id != null ? id.hashCode() : 0;
		result = 31 * result + (portNumber != null ? portNumber.hashCode() : 0);
		result = 31 * result + (portState != null ? portState.hashCode() : 0);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(Integer portNumber) {
		this.portNumber = portNumber;
	}

	public PortState getPortState() {
		return portState;
	}

	public void setPortState(PortState portState) {
		this.portState = portState;
	}

	public String getServiceUsedFor() {
		return serviceUsedFor;
	}

	public void setServiceUsedFor(String serviceUsedFor) {
		this.serviceUsedFor = serviceUsedFor;
	}

	public ScanDetails getScanDetail() {
		return scanDetail;
	}

	public void setScanDetail(ScanDetails scanDetail) {
		this.scanDetail = scanDetail;
	}

}
