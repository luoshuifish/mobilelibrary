package com.mobilelibrary.handlerexception;

//Å×³öÒì³£
public class WSError extends Throwable {
	
	private static final long serialVersionUID = 1L;
	
	private String message;

	public WSError() {
	}
	
	public WSError(String message) {
		this.message = message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
