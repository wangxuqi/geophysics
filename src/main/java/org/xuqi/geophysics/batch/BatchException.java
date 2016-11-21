package org.xuqi.geophysics.batch;

public class BatchException extends RuntimeException {
	private static final long serialVersionUID = 916163809455877983L;

	public BatchException(String message) {
		super(message);
	}

	public BatchException(String message, Exception cause) {
		super(message, cause);
	}

	public BatchException(Exception e) {
		super(e);
	}
}
