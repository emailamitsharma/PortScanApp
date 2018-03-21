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
@ResponseStatus(code=HttpStatus.INTERNAL_SERVER_ERROR)
public class InvalidProcessingException extends Exception{
	private static final long serialVersionUID = 1L;

	public InvalidProcessingException(String exception) {
		super(exception);
	}

	public InvalidProcessingException() {
		super();
	}
}
