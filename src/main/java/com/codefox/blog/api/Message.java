package com.codefox.blog.api;

public class Message {

	private String message;
	private int status;
	@Override
	public String toString() {
		return "Message [message=" + message + ", status=" + status + "]";
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
