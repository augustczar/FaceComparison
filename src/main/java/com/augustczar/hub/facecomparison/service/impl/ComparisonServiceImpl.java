package com.augustczar.hub.facecomparison.service.impl;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augustczar.hub.facecomparison.exception.InvalidImageException;
import com.augustczar.hub.facecomparison.service.ComparisonService;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.BoundingBox;
import software.amazon.awssdk.services.rekognition.model.CompareFacesMatch;
import software.amazon.awssdk.services.rekognition.model.CompareFacesRequest;
import software.amazon.awssdk.services.rekognition.model.CompareFacesResponse;
import software.amazon.awssdk.services.rekognition.model.ComparedFace;
import software.amazon.awssdk.services.rekognition.model.Image;
import software.amazon.awssdk.services.rekognition.model.RekognitionException;
/**
 * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/java_rekognition_code_examples.html
 */
@Service
public class ComparisonServiceImpl implements ComparisonService {

	@Autowired
	private RekognitionClient rekognitionClient;

	 Float SIMILARITY_THRESHOLD = 10F;
	 Float MINIMAL_SIMILARITY = 0F;
	 
	@Override
	public float compareTwoPhotos(String photo1, String photo2) throws RekognitionException, IOException {
		float result = 0f;
		try {
			SdkBytes sourceBytes = SdkBytes
					.fromByteArray(Base64.getDecoder()
							.decode(photo1.substring(photo1.indexOf(',') + 1)));
			SdkBytes targetBytes = SdkBytes
					.fromByteArray(Base64.getDecoder()
							.decode(photo2.substring(photo2.indexOf(',') + 1)));

			// Create an Image object for the source image.
			Image souImage = Image.builder().bytes(sourceBytes).build();

			Image tarImage = Image.builder().bytes(targetBytes).build();

			CompareFacesRequest facesRequest = CompareFacesRequest.builder()
					.sourceImage(souImage)
					.targetImage(tarImage)
					.similarityThreshold(SIMILARITY_THRESHOLD)
					.build();

			// Compare the two images.
			CompareFacesResponse compareFacesResult = rekognitionClient.compareFaces(facesRequest);
			System.out.println(compareFacesResult.toString());
			
			List<CompareFacesMatch> faceDetails = compareFacesResult.faceMatches();
			for (CompareFacesMatch match : faceDetails) {
				ComparedFace face = match.face();
				BoundingBox position = face.boundingBox();
				System.out.println("Face at " + position.left().toString() + " " + position.top() + " matches with "
						+ face.confidence().toString() + "% confidence.");

			}
			List<ComparedFace> uncompared = compareFacesResult.unmatchedFaces();
			System.out.println("There was " + uncompared.size() + " face(s) that did not match");
			System.out.println("Source image rotation: " + compareFacesResult.sourceImageOrientationCorrection());
			System.out.println("target image rotation: " + compareFacesResult.targetImageOrientationCorrection());

            // Separa o MAIOR RESULTADO!
            Optional<CompareFacesMatch> resultMatchFaceMatch = faceDetails.stream().max(Comparator.comparing(a -> a.similarity().floatValue()));
            result = resultMatchFaceMatch.isPresent() ? resultMatchFaceMatch.get().similarity().floatValue() : MINIMAL_SIMILARITY;
            
		} catch (InvalidParameterException e) {
			throw new InvalidImageException();
		}
		return result;
	}
}
