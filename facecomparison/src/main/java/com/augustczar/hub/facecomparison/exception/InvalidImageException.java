package com.augustczar.hub.facecomparison.exception;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "image1 or image2 are not valid or do not have faces to compare.")
public class InvalidImageException extends IOException {

	private static final long serialVersionUID = 4482808531407020786L;

}
