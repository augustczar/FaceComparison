package com.augustczar.hub.facecomparison.service;

import java.io.IOException;

import software.amazon.awssdk.services.rekognition.model.RekognitionException;

public interface ComparisonService {

	 float compareTwoPhotos(String photo1, String photo2) throws RekognitionException, IOException;
}
