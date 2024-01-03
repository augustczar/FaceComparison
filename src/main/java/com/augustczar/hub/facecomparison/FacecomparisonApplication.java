package com.augustczar.hub.facecomparison;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import software.amazon.awssdk.services.rekognition.RekognitionClient;

@SpringBootApplication
@ComponentScan(basePackages = {"com.augustczar.hub.facecomparison"})
public class FacecomparisonApplication {

	public static void main(String[] args) {
		SpringApplication.run(FacecomparisonApplication.class, args);
	}
	
	@Bean
	RekognitionClient rekognitionClient() {
		return RekognitionClient.create();
	}
}
