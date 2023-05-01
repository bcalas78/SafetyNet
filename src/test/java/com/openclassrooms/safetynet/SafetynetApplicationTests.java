package com.openclassrooms.safetynet;

import com.openclassrooms.safetynet.controller.FirestationController;
import com.openclassrooms.safetynet.controller.IndexController;
import com.openclassrooms.safetynet.controller.MedicalRecordController;
import com.openclassrooms.safetynet.controller.PersonController;
import com.openclassrooms.safetynet.service.DatabaseManipulation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class SafetynetApplicationTests {

	@Autowired
	private ApplicationContext context;

	@Autowired
	private SafetynetApplication safetynetApplication;

	@Autowired
	private DatabaseManipulation databaseManipulation;

	@Autowired
	private IndexController indexController;

	@Autowired
	private PersonController personController;

	@Autowired
	private FirestationController firestationController;

	@Autowired
	private MedicalRecordController medicalRecordController;

	@Test
	void contextLoads() {
		assertThat(context).isNotNull();
		assertThat(safetynetApplication).isNotNull();
		assertThat(databaseManipulation).isNotNull();
		assertThat(indexController).isNotNull();
		assertThat(personController).isNotNull();
		assertThat(firestationController).isNotNull();
		assertThat(medicalRecordController).isNotNull();
	}

}
