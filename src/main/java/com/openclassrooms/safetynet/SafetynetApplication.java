package com.openclassrooms.safetynet;

import com.openclassrooms.safetynet.service.DatabaseManipulation;
import com.openclassrooms.safetynet.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class SafetynetApplication {

	private static final Logger logger = LogManager.getLogger(PersonService.class);

	@Autowired
	private DatabaseManipulation databaseManipulation;

	public static void main(String[] args) {
		SpringApplication.run(SafetynetApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void readJson() throws IOException {
		try {
			logger.info("JSON File read with success at the lauch of the application Safetynet.");
		} catch (Exception e) {
			logger.error("Cannot read JSON file", e);
		}

		databaseManipulation.init();
	}
}



