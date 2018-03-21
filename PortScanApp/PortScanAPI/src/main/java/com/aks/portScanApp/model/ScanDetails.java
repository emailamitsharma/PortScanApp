/**
 * 
 */
package com.aks.portScanApp.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.aks.portScanApp.util.JavaToSQLDateTimeConvertor;

/**
 * @author Amit.Sharma
 *
 */
@Entity
@Table(name="scan_details")
public class ScanDetails extends PersistenceData implements Serializable {

	private static final long serialVersionUID = -7232845268778991646L;

	@Id
	@Column(name="scan_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long scanId;

	@Column(name="identifier")
	private String identifier;

	@Column(name="scan_time")
	@Convert(converter=JavaToSQLDateTimeConvertor.class)
	private LocalDateTime scanTimeStamp;
	
	@Column(name="is_scan_completed")
	private boolean isCompleted;

	@OneToMany(mappedBy="scanDetail",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	private Set<NMapData> nmapData;

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Long getScanId() {
		return scanId;
	}

	public void setScanId(Long scanId) {
		this.scanId = scanId;
	}

	public LocalDateTime getScanTimeStamp() {
		return scanTimeStamp;
	}

	public void setScanTimeStamp(LocalDateTime scanTimeStamp) {
		this.scanTimeStamp = scanTimeStamp;
	}	

	
	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public Set<NMapData> getNmapData() {
		return nmapData;
	}

	public void setNmapData(Set<NMapData> nmapData) {
		this.nmapData = nmapData;
	}
	@Override
	public String toString() {
		return "Scan  [id=" + scanId + ", identifier =" + identifier + ", scan_time=" + scanTimeStamp  + "]";
	}



}
