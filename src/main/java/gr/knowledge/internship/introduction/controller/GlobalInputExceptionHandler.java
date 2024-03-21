package gr.knowledge.internship.introduction.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalInputExceptionHandler {
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> handleException(IllegalArgumentException iae) {
		Map<String, Object> body = new HashMap<>();
		body.put(iae.getMessage(), iae);
		return new ResponseEntity<>(body, HttpStatus.NOT_ACCEPTABLE);
	}
}
