package gr.knowledge.internship.introduction.controller;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalPropertyValueExceptionController {
	@ExceptionHandler(PropertyValueException.class)
	public ResponseEntity<Object> handleException(PropertyValueException pve) {
		Map<String, Object> body = new HashMap<>();
		body.put(pve.getMessage(), pve.getCause());

		return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
