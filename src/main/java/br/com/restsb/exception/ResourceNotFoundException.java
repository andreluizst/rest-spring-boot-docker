package br.com.restsb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	
	public ResourceNotFoundException(String exception) {
		super(exception);
	}
	
	public ResourceNotFoundException(String exception, Throwable cause) {
		super(exception, cause);
	}
}
