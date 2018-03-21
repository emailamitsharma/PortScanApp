/**
 * 
 */
package com.aks.portScanApp.cucumber.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.aks.portScanApp.PortScanApp;

/**
 * @author Amit.Sharma
 *
 */

@SpringBootTest(classes=PortScanApp.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes=PortScanApp.class)
@AutoConfigureMockMvc
public class CucumberStepDefBasicConf {

	@Value("${baseURLOfAPI}")
	public String baseURLForAPI;
}
