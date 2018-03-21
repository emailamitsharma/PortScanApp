/**
 * 
 */
package com.aks.portScanApp.controller;


import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.aks.portScanApp.excception.ErrorMessageConstants;
import com.aks.portScanApp.excception.MoreInfo;
import com.aks.portScanApp.excception.PortScanErrorDetails;

/**
 * @author Amit.Sharma
 *
 */
@RestController
@RestControllerAdvice
public class ErrorHandlingController implements ErrorController{


	@Order(Ordered.HIGHEST_PRECEDENCE)
	@ExceptionHandler(NoHandlerFoundException.class)
	public final ResponseEntity<PortScanErrorDetails> handleNoHanlderFoundException(NoHandlerFoundException ex, WebRequest request) {
		MoreInfo moreInfo = new MoreInfo();
		PortScanErrorDetails errorDetails = (PortScanErrorDetails.getPortScanErrorBuilder(HttpStatus.NOT_FOUND, ex.getMessage() != null? ex.getMessage():ErrorMessageConstants.defaultErrorMessage, ErrorMessageConstants.resourceNotFoundDeveloperMessage)).setMoreInfo(moreInfo).build();
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}
	

    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public final ResponseEntity<PortScanErrorDetails> handleGlobalUnHandledException(Exception ex, WebRequest request) {
		MoreInfo moreInfo = new MoreInfo();
		PortScanErrorDetails errorDetails = (PortScanErrorDetails.getPortScanErrorBuilder(HttpStatus.INTERNAL_SERVER_ERROR,ErrorMessageConstants.defaultErrorMessage,ex.getMessage() != null? ex.getMessage():ErrorMessageConstants.defaultErrorMessage)).setMoreInfo(moreInfo).build();
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

    @Override
    public String getErrorPath() {
        return PATH;
    }
}