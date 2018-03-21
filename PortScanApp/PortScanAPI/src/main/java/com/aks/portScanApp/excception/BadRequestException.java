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
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends Exception {
	private static final long serialVersionUID = 1L;

	
	public BadRequestException(String exception) {
	    super(exception);
	  }

	public BadRequestException() {
	}
}
