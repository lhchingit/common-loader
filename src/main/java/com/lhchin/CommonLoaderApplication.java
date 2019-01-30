package com.lhchin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.lhchin.config.DataSourceConfiguration;
import com.lhchin.config.TaskConfiguration;

@SpringBootApplication
public class CommonLoaderApplication implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(CommonLoaderApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CommonLoaderApplication.class, args);
	}

	@Autowired
	DataSourceConfiguration dataSourceConfig;

	@Autowired
	TaskConfiguration taskConfiguration;

	@Override
	public void run(String... args) throws Exception {
		logger.info(dataSourceConfig.getSettings().toString());
		logger.info(taskConfiguration.getSettings().toString());
	}
}
