package com.glomidco.maven.plugins.tibco.command;

import java.io.File;

public class LibBuilderCommand extends AbstractDesignerCommand {
	private File outputLibFile;
	private String uri;
	private String pathToAliases;
	private String traFile;
	private File projectDir;
	
	public LibBuilderCommand(String command, String traFile, File projectDir, String uri, File outputLibFile, String pathToAliases) {
		super(command);
		this.traFile = traFile;
		this.pathToAliases = pathToAliases;
//		addArgument("-p", projectDir);
		this.outputLibFile = outputLibFile;
		this.uri = uri;
		this.projectDir = projectDir;

	}

	@Override
	public File getProjectFolder() {
		return projectDir;
	}

	@Override
	public String getUri() {
		return uri;
	}

	@Override
	public File getOutputFile() {
		return outputLibFile;
	}

	@Override
	public boolean hideLibraries() {
		return true;
	}

	@Override
	public String getPathToAliases() {
		return pathToAliases;
	}

	@Override
	public boolean getOverwriteOutputFile() {
		return true;
	}

	@Override
	public String getPropertiesFile() {
		return traFile;
	}

}
