package com.glomidco.maven.plugins.tibco.command;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;

abstract class AbstractDesignerCommand implements DesignerCommand {
	private String command;
	private List<String> arguments;
	
	public AbstractDesignerCommand(String command) {
		this.command = command;
		arguments = new ArrayList<String>();
	}
	
	public void addArgument(String key) {
		arguments.add(key);
	}

	public abstract File getProjectFolder();

	public abstract String getUri();

	public abstract File getOutputFile();

	public abstract boolean hideLibraries();

	public abstract String getPathToAliases();

	public abstract boolean getOverwriteOutputFile();

	public abstract String getPropertiesFile();
	
	@Override
	public void execute() throws DesignerCommandException {
		String command = getCommand();
		System.out.println("Executing: " + command);
		try {
			CommandLine cmdLine = CommandLine.parse(command);
			DefaultExecutor executor = new DefaultExecutor();
			int exitValue = executor.execute(cmdLine);
		} catch (Exception e) {
			throw new DesignerCommandException(e);
		}
	}
	
	private String getCommand() {
		StringBuffer buf = new StringBuffer();
		buf.append(command);
		buf.append(" --propFile ");
		buf.append(getPropertiesFile());
		String pathToAliases = getPathToAliases();
		if (pathToAliases != null) {
			buf.append(" -a ");
			buf.append(pathToAliases);
		}
		String uri = getUri();
		if (uri != null) {
			buf.append(" -lib ");
			buf.append(uri);
		}
		File outputFile = getOutputFile();
		if (outputFile != null) {
			buf.append(" -o ");
			buf.append(getOutputFile());
		}
		// TODO: cleanup. Don't like this instanceof construction
		if (this instanceof ValidateProjectCommand) {
			buf.append(" ");
		} else {
			buf.append(" -p ");
		}
		buf.append(getProjectFolder());
		if (getOverwriteOutputFile()) {
			buf.append(" -x ");
		}
		return buf.toString();
	}
}
