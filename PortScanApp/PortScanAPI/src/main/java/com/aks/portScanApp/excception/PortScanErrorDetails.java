/**
 * 
 */
package com.aks.portScanApp.excception;

import org.springframework.http.HttpStatus;

/**
 * @author Amit.Sharma
 *
 */
public class PortScanErrorDetails {

	private String code;

	private String message;

	private String developerMessage;

	private MoreInfo moreInfo;

	private PortScanErrorDetails(PortScanErrorDetailsBuilder builder){
		setCode(builder.code);
		this.message = builder.message;
		this.developerMessage = builder.developerMessage;
		this.moreInfo = builder.moreInfo;
	}
	

	public String getCode() {
		return code;
	}

	private void setCode(HttpStatus code) {
		this.code = code.toString();
	}

	public static class PortScanErrorDetailsBuilder {
		private HttpStatus code;

		private String message;

		private String developerMessage;

		private MoreInfo moreInfo;

		public PortScanErrorDetailsBuilder(HttpStatus buildHttpStatusCode, String friendlyMessage, String buildDeveloperMessage){
			this.code = buildHttpStatusCode;
			this.message = friendlyMessage;
			this.developerMessage = buildDeveloperMessage;
		}

		public PortScanErrorDetailsBuilder() {
			super();
		}

		public PortScanErrorDetailsBuilder setMoreInfo(MoreInfo buildMoreInfo){
			this.moreInfo = buildMoreInfo;
			return this;
		}

		public PortScanErrorDetails build(){
			return new PortScanErrorDetails(this);
		}
	}

	public String getMessage() {
		return message;
	}

	public String getDeveloperMessage() {
		return developerMessage;
	}

	public MoreInfo getMoreInfo() {
		return moreInfo;
	}
	
	public static PortScanErrorDetailsBuilder getPortScanErrorBuilder(HttpStatus buildHttpStatusCode, String friendlyMessage, String buildDeveloperMessage){
		return new PortScanErrorDetailsBuilder(buildHttpStatusCode,friendlyMessage,buildDeveloperMessage);
	}

}
