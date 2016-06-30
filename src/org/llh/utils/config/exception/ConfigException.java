package org.llh.utils.config.exception;

public class ConfigException extends Exception{

	private static final long serialVersionUID = -7156040856443564194L;

	public ConfigException() {
		super();
	}

	public ConfigException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ConfigException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConfigException(String message) {
		super(message);
	}

	public ConfigException(Throwable cause) {
		super(cause);
	}
}
