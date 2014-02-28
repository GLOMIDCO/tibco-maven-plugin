package com.glomidco.maven.plugins.tibco.command;

public class LibBuilderCommand extends AbstractCommand {

	public LibBuilderCommand(String command, String workingDir, String uri, String projectDir, String outputLibFile, String pathToAliases) {
		super(command);
		
		setWorkingDir(workingDir);
		
		if (pathToAliases != null && pathToAliases != "") {
			addArgument("-a", pathToAliases);
		}
		addArgument("-p", projectDir);
		addArgument("-o", outputLibFile);
		addArgument("-lib", uri);
		addArgument("-x");
	}

}
