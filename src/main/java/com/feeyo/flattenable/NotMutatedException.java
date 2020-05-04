package com.feeyo.flattenable;

public class NotMutatedException extends RuntimeException {

	private static final long serialVersionUID = 4852152347366253876L;

	public NotMutatedException(String message) {
		super(message);
	}

	public NotMutatedException(String message, Throwable cause) {
		super(message, cause);
	}
}
