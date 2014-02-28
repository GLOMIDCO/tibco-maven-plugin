package com.glomidco.maven.plugins.tibco.command;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;

abstract class AbstractCommand implements Command {
	private String command;

	public AbstractCommand(String command) {
		this.command = command;
	}
	
	public void run() throws Exception {
		// TODO Auto-generated method stub
	}
	
	public void addArgument(String key) {
		
	}
	
	public void addArgument(String key, String value) {
		
	}
	
	public void setWorkingDir(String workingDir) {
		
	}
	
	/**
	 * 
	 * @param timeout
	 * @throws TimeoutException execution took longer than specified in timeout
	 * @throws IOException Could not execute command
	 */
	public void executeAndWatch(long timeout) throws TimeoutException, IOException {
		CommandLine cmdLine = CommandLine.parse(command);
		DefaultExecutor executor = new DefaultExecutor();
		executor.setExitValue(1);
		ExecuteWatchdog watchdog = new ExecuteWatchdog(timeout);
		executor.setWatchdog(watchdog);
		int exitValue = executor.execute(cmdLine);
		if (executor.isFailure(exitValue) && watchdog.killedProcess()) {
			throw new TimeoutException();
		}
	}
}
