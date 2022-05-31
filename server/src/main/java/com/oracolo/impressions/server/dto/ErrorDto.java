package com.oracolo.impressions.server.dto;

public class ErrorDto {
	private String error, exceptionMessage;

	public ErrorDto(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
		this.error="Error during csv parsing";
	}
	public ErrorDto(){}

	public String getError() {
		return error;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setError(String error) {
		this.error = error;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	@Override
	public String toString() {
		return "ErrorDto{" + "error='" + error + '\'' + ", exceptionMessage='" + exceptionMessage + '\'' + '}';
	}
}
