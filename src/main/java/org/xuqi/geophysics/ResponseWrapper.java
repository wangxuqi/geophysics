package org.xuqi.geophysics;

import java.io.Serializable;

public class ResponseWrapper<T> implements Serializable {
	private static final long serialVersionUID = 2090027279506478224L;
	private String message;
	private int code;
	private T data;

	public ResponseWrapper() {

	}

	public ResponseWrapper(String message, int code) {
		this.message = message;
		this.code = code;
	}

	public ResponseWrapper(String message, int code, T data) {
		this.message = message;
		this.code = code;
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}