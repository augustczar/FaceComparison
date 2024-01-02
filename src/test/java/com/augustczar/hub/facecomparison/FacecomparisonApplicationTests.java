package com.augustczar.hub.facecomparison;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = FacecomparisonApplication.class)
@ActiveProfiles(value = "test")
class FacecomparisonApplicationTests {

	@BeforeAll
	static void setupProperties() {
		System.setProperty("aws.region", "us-east-1");
		System.setProperty("aws.accessKeyId", "localstack");
		System.setProperty("aws.secretAccessKey", "localstack");
	}
	
	@Test
	void contextLoads() {
	}

}
