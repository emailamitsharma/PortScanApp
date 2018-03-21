/**
 * 
 */
package com.aks.portScanApp.excception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Amit.Sharma
 *
 */
@ResponseStatus(HttpStatus.OK)
public class NoDataFoundException extends Exception {
	
	private static final long serialVersionUID = 1L;

	
	public NoDataFoundException(String exception) {
	    super(exception);
	  }

	public NoDataFoundException() {
	}

}
