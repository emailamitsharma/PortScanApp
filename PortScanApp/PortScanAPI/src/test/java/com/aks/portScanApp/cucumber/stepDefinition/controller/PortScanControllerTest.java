/**
 * 
 */
package com.aks.portScanApp.cucumber.stepDefinition.controller;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.aks.portScanApp.cucumber.configuration.CucumberStepDefBasicConf;
import com.aks.portScanApp.service.CommandExecutor;

import cucumber.api.Scenario;
import cucumber.api.java8.En;

/**
 * @author Amit.Sharma
 *
 */
public class PortScanControllerTest  extends CucumberStepDefBasicConf implements En {

	public static final Logger logger = LoggerFactory.getLogger(PortScanControllerTest.class);
	
	@SuppressWarnings("unused")
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	private ResponseEntity<?> testRestCallResponse; 

	private String restApiUrl = baseURLForAPI;
	
	@Value("${nmap.exec.mockValue}")
	private String mockNMapValue;
	
	@MockBean
	@Autowired
	private CommandExecutor nmapExecutorService;
		

	PortScanControllerTest() {
		
		 Before(new String[]{"@DevTest"}, 0, 1, (Scenario scenario) -> {
			 testRestCallResponse = null;
	        });
	
		Before(()-> {logger.info("Nothing to setup in before any of test case is fired.But you can add your stuff in future");});

		Given("^REST-API end point is appended as baseURL \\+ \"([^\"]*)\"$", (String arg1) -> {
			restApiUrl = baseURLForAPI + arg1;
		});

		When("^Connection is tried to reach out with given request$", () -> {			
			Mockito.when(nmapExecutorService.execute(Mockito.any())).thenReturn(mockNMapValue);
			testRestCallResponse = this.testRestTemplate.getForEntity(restApiUrl, Object.class, new HashMap<>());
		});

		Then("^Response is returned$", () -> {
			Assert.assertNotNull(testRestCallResponse);
		});
		
		Given("^REST-API end point is available as baseURL$", () -> {
		  restApiUrl = baseURLForAPI = baseURLForAPI!=null?baseURLForAPI:"/api/ports";
		});

		Given("^request is appended with google\\.com and false$", () -> {
		    restApiUrl = baseURLForAPI+ "?input=google.com&isLatestScanRequired=false";
		});

		Given("^request is appended with akshima\\.in and false$", () -> {
		    restApiUrl = baseURLForAPI+ "?input=akshima.in&isLatestScanRequired=false";
		});

		Then("^status code is (\\d+)$", (Integer arg1) -> {
			Assert.assertEquals(String.valueOf(arg1), testRestCallResponse.getStatusCode().toString());
		});

		Then("^data type is of application/json\\.$", () -> {
			Assert.assertThat(testRestCallResponse.getHeaders().getContentType().toString(), CoreMatchers.containsString(MediaType.APPLICATION_JSON_VALUE));
		});

		Then("^size of data is (.*)$", (String arg1) -> {
			Assert.assertNotNull(testRestCallResponse);
		});
		
		Then("^Successful response is returned$", () -> {
		  Assert.assertNotNull(testRestCallResponse);
		});

		Then("^all ports are open in state\\.$", () -> {
			Assert.assertFalse(testRestCallResponse.getBody().toString().contains("closed"));
		});

		Then("^results of the latest scan appear as well as the history of previous scans for that host$", () -> {
			Assert.assertTrue(StringUtils.countMatches(testRestCallResponse.getBody().toString(), "80") >1);
		});
		
		After(new String[]{"@DevTest"},0,1,(Scenario sc)-> {logger.info("Nothing to close out after all test case have been fired.But you can add your stuff in future");});
	}
}
