package com.teamrandom.softproj.exception;

public class RandomException extends Exception {
	
	public RandomException(String message) {
		super(message);
	}
	
	public RandomException(String message, Throwable cause) {
		super(message, cause);
	}
}
