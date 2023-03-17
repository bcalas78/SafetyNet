package com.openclassrooms.safetynet;

import com.openclassrooms.safetynet.service.DatabaseManipulation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class SafetynetApplication {

	@Autowired
	private DatabaseManipulation databaseManipulation;

	public static void main(String[] args) {
		SpringApplication.run(SafetynetApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void readJson() throws IOException {
		System.out.println("lire le json au démarrage de l'application");

		databaseManipulation.init();
	}
}



