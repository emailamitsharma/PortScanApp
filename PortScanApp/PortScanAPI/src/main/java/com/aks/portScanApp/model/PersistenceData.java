/**
 * 
 */
package com.aks.portScanApp.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Amit.Sharma
 *
 */
public class PersistenceData implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Column(name="created_date",updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime createdDate;
	
	@Column(name="updated_date")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime updatedDate;
	
	@Column(name="created_by")
	private String createdBy="SYSTEM";
	
	@Column(name="updated_by")
	private String updatedBy;
	

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

}
