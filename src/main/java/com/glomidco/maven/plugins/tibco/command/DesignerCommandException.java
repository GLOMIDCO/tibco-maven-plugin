package com.glomidco.maven.plugins.tibco.command;

public class DesignerCommandException extends Exception {
	public DesignerCommandException(String key) {
		super(key);
	}
	
	public DesignerCommandException(String key, Throwable cause) {
		super(key, cause);
	}
	
	public DesignerCommandException(Throwable cause) {
		super(cause);
	}
}
