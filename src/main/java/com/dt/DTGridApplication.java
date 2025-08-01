package com.dt;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.log4j.Log4j2;

@Log4j2
@EnableAsync
@SpringBootApplication
public class DTGridApplication extends SpringBootServletInitializer implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(DTGridApplication.class, args);
		log.info(DTGridApplication.class.getSimpleName() + " started.");
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DTGridApplication.class);
	}

	@Bean
	Executor asyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("DTGridThread-");
		executor.initialize();
		return executor;
	}
}
