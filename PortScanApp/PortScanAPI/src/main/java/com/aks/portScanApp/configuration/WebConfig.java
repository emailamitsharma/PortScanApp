/**
 * 
 */
package com.aks.portScanApp.configuration;

import org.h2.server.web.WebServlet;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Amit.Sharma
 *
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter implements EmbeddedServletContainerCustomizer {

  /**
    * Setup a simple strategy: use all the defaults and return JSON by default when not sure. 
    */
  @Override
  public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    configurer.defaultContentType(MediaType.APPLICATION_JSON)
    		  .favorPathExtension(true)
    		  .favorParameter(true)
    		  .parameterName("format")
    		  .mediaType("xml", MediaType.APPLICATION_XML)
    		  .mediaType("json", MediaType.APPLICATION_JSON)
    		  .mediaType("text", MediaType.TEXT_PLAIN);
  }
  
  @Override
  public void customize(ConfigurableEmbeddedServletContainer container) {
      container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404"));
  }
  
  @Bean
  ServletRegistrationBean h2servletRegistration(){
      ServletRegistrationBean registrationBean = new ServletRegistrationBean( new WebServlet());
      registrationBean.addUrlMappings("/console/*");
      return registrationBean;
  }
}
