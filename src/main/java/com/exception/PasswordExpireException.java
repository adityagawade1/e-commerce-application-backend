package com.exception;

public class PasswordExpireException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PasswordExpireException(String message) {
		super(message);
	}
}
