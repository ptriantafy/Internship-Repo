package gr.knowledge.internship.introduction.handler;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.PropertyValueException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import gr.knowledge.internship.introduction.exception.SeasonNotFoundException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler {


	@ExceptionHandler(SeasonNotFoundException.class)
	public ResponseEntity<Object> handleEntityNotFoundException(SeasonNotFoundException snf) {
		Map<String, Object> body = new HashMap<>();
		body.put(snf.getMessage(), snf);

		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException iae) {
		Map<String, Object> body = new HashMap<>();
		body.put(iae.getMessage(), iae);
		return new ResponseEntity<>(body, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(PropertyValueException.class)
	public ResponseEntity<Object> handleProperyValueException(PropertyValueException pve) {
		Map<String, Object> body = new HashMap<>();
		body.put(pve.getMessage(), pve.getCause());

		return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
