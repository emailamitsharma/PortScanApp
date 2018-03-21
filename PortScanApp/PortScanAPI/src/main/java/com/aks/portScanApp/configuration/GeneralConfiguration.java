/**
 * 
 */
package com.aks.portScanApp.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author r56529
 *
 */
@Configuration
public class GeneralConfiguration {
	
	@Value("${threadPool.maxPoolSize:5}")
	private int maxPoolSize;
	
	@Value("${threadPool.corePooSize:5}")
	private int corePoolSize;

	@Bean
	public ThreadPoolTaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
		pool.setCorePoolSize(corePoolSize);
		pool.setMaxPoolSize(maxPoolSize);
		pool.setWaitForTasksToCompleteOnShutdown(true);
		return pool;
	}
}
