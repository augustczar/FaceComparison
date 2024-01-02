package com.augustczar.hub.facecomparison.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.augustczar.hub.facecomparison.service.ComparisonService;

@RestController
@RequestMapping("/facecomparison")
public class ComparisonController {
	
	@Autowired
	private ComparisonService comparisonService;

	@GetMapping(path = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
	public Status getStatus() {
		return new Status("OK And Running!");
	}
	
	@PostMapping(path = "/compare", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Output compare(@RequestBody Input input) throws Exception{
		try {
			float result = comparisonService.compareTwoPhotos(input.photo1(), input.photo2());
			return new Output(result);
		} catch (Exception e) {
			e.printStackTrace();
			return new Output(-1);
		}
	}
	
	record Status(String status) {
	}	
	record Input(String photo1, String photo2) {
	}	
	record Output(float similarity) {
	}
}
