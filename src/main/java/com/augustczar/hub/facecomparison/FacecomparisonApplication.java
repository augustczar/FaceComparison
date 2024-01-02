package com.augustczar.hub.facecomparison;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import software.amazon.awssdk.services.rekognition.RekognitionClient;

@SpringBootApplication
public class FacecomparisonApplication {

	public static void main(String[] args) {
		SpringApplication.run(FacecomparisonApplication.class, args);
	}
	
	@Bean
	RekognitionClient rekognitionClient() {
		return RekognitionClient.create();
	}
}
