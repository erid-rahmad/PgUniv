package com.mpc.pgateway.model;

public class UMPPBaseResponse {
	protected String code;
	protected String message;
	
	public UMPPBaseResponse() {
		super();
	}

	public UMPPBaseResponse(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "UMPPBaseResponse [code=" + code + ", message=" + message + "]";
	}
}
