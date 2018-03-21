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
public class DataSourceException extends java.sql.SQLException {

	private static final long serialVersionUID = 1L;

	public DataSourceException(String exception) {
		super(exception);
	}

	public DataSourceException() {
		super();
	}

}
