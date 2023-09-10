package com.utils;

public class ResponseTo {

	private int code;
	private String message;
	private Object entity;

	public ResponseTo(int code, String message, Object entity) {
		super();
		this.code = code;
		this.message = message;
		this.entity = entity;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getEntity() {
		return entity;
	}

	public void setEntity(Object entity) {
		this.entity = entity;
	}

}
