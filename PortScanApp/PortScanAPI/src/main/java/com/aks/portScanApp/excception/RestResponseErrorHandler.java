package com.aks.portScanApp.excception;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class RestResponseErrorHandler implements ResponseErrorHandler{
	
	public static final Logger logger = LoggerFactory.getLogger(RestResponseErrorHandler.class);

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
    	logger.error("Response error:"+ response.getStatusCode() + ": "+ response.getStatusText());
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
    	logger.error("Response hasError:"+ response.getStatusCode() + ": "+ response.getStatusText());
        return isError(response.getStatusCode());
    }
    
    public static boolean isError(HttpStatus status) {
        HttpStatus.Series series = status.series();
        return (HttpStatus.Series.CLIENT_ERROR.equals(series)
                || HttpStatus.Series.SERVER_ERROR.equals(series));
    }

}
