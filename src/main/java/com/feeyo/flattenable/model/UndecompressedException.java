package com.feeyo.flattenable.model;

public class UndecompressedException extends RuntimeException {

	private static final long serialVersionUID = 2060307101478788727L;

	public UndecompressedException() {
		super();
	}

	public UndecompressedException(String message) {
		super(message);
	}

	public UndecompressedException(String message, Throwable cause) {
		super(message, cause);
	}
}
