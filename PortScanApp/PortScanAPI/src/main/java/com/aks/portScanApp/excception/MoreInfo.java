/**
 * 
 */
package com.aks.portScanApp.excception;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author Amit.Sharma
 *
 */
public class MoreInfo {
	
	@Value("${defaultContactEmail:mr.amitksharma@gmail.com}")
	public String contactEmail;
	
	public String moreDetails;

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getMoreDetails() {
		return moreDetails;
	}

	public void setMoreDetails(String moreDetails) {
		this.moreDetails = moreDetails;
	}

}
