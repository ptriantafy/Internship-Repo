package gr.knowledge.internship.introduction.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalEntityExceptionHandler {
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> handleException(EntityNotFoundException enf) {
		Map<String, Object> body = new HashMap<>();
		body.put(enf.getMessage(), enf);

		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}
}
