package com.exception;

import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.AccountLockedException;

import org.apache.catalina.connector.Response;
import org.apache.logging.log4j.Level;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.utils.Constants;
import com.utils.Headers;
import com.utils.LOG;
import com.utils.ResponseTo;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	private ResponseEntity<ResponseTo> handleValidationException(MethodArgumentNotValidException e) {

		Map<String, String> errors = new HashMap<>();

		e.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		return new ResponseEntity<>(new ResponseTo(HttpStatus.BAD_REQUEST.value(), Constants.FAILURE, errors),Headers.getHeader(),
				HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ResponseTo> userNotFound(UserNotFoundException e) {

		LOG.error(Level.ERROR, Constants.FAILURE + e.getMessage(), e);

		return new ResponseEntity<>(new ResponseTo(HttpStatus.BAD_REQUEST.value(), Constants.FAILURE, e.getMessage()),Headers.getHeader(),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(UnAuthorizedException.class)
	public ResponseEntity<ResponseTo> unAuthorizedException(UnAuthorizedException e) {

		LOG.error(Level.ERROR, Constants.FAILURE + e.getMessage(), e);

		return new ResponseEntity<>(new ResponseTo(HttpStatus.BAD_REQUEST.value(), Constants.FAILURE, e.getMessage()),Headers.getHeader(),
				HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(PasswordExpireException.class)
	public ResponseEntity<ResponseTo> apasswordExpirationException(PasswordExpireException e) {

		LOG.error(Level.ERROR, Constants.FAILURE + e.getMessage(), e);

		return new ResponseEntity<>(new ResponseTo(HttpStatus.BAD_REQUEST.value(), Constants.FAILURE, e.getMessage()),Headers.getHeader(),
				HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ResponseTo> badCredentialException(BadCredentialsException e) {
		LOG.error(Level.ERROR, Constants.FAILURE + e.getMessage(), e);

		return new ResponseEntity<>(new ResponseTo(HttpStatus.BAD_REQUEST.value(), Constants.FAILURE, e.getMessage()),Headers.getHeader(),
				HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(AccountLockedException.class)
	public ResponseEntity<ResponseTo> accounLockException(AccountLockedException e) {
		LOG.error(Level.ERROR, Constants.FAILURE + e.getMessage(), e);

		return new ResponseEntity<>(new ResponseTo(HttpStatus.BAD_REQUEST.value(), Constants.FAILURE, e.getMessage()),Headers.getHeader(),
				HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(CommonException.class)
	public ResponseEntity<ResponseTo> commonException(CommonException e){
		LOG.error(Level.ERROR, Constants.FAILURE + e.getMessage(), e);

		return new ResponseEntity<>(new ResponseTo(HttpStatus.BAD_REQUEST.value(), Constants.FAILURE, e.getMessage()),Headers.getHeader(),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseTo> handleException(Exception e) {

		LOG.error(Level.ERROR, Constants.FAILURE + e.getMessage(), e);

		return new ResponseEntity<>(
				new ResponseTo(HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.FAILURE, e.getMessage()),
				Headers.getHeader(), HttpStatus.INTERNAL_SERVER_ERROR);

	}
}
