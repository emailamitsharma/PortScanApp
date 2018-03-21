package com.aks.portScanApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.aks.portScanApp.configuration.JpaConfiguration;
import com.aks.portScanApp.configuration.WebConfig;


@Import({JpaConfiguration.class,WebConfig.class})
@SpringBootApplication(scanBasePackages={"com.aks.portScanApp"})
public class PortScanApp {

	public static void main(String[] args) {
		SpringApplication.run(PortScanApp.class, args);
	}
}
