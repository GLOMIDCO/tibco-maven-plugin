package com.glomidco.maven.plugins.tibco.command;

public class EarBuilderCommand extends AbstractCommand {

	public EarBuilderCommand(String earBuildCommand, String workingDir, String uri, String projectDir, String outputEarFile, String pathToAliases) {
		super(earBuildCommand);
		
		setWorkingDir(workingDir);
		
		if (pathToAliases != null && pathToAliases != "") {
			addArgument("-a", pathToAliases);
		}
		addArgument("-p", projectDir);
		addArgument("-o", outputEarFile);
		addArgument("-ear", uri);
		addArgument("-x");
	}
}
