package com.glomidco.maven.plugins.tibco.command;

import java.io.File;

public class EarBuilderCommand extends AbstractDesignerCommand {
	private File outputEarFile;
	private String uri;
	private String pathToAliases;
	private String traFile;
	private File projectDir;
	
	public EarBuilderCommand(String command, String traFile, File projectDir, String uri,  File outputEarFile, String pathToAliases) {
		super(command);
		this.projectDir = projectDir;
		this.traFile = traFile;
		this.projectDir = projectDir;
		this.uri = uri;
		this.outputEarFile = outputEarFile;
		this.pathToAliases = pathToAliases;
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
		return outputEarFile;
	}

	@Override
	public boolean hideLibraries() {
		return false;
	}

	@Override
	public String getPathToAliases() {
		return pathToAliases;
	}

	@Override
	public boolean getOverwriteOutputFile() {
		return false;
	}

	@Override
	public String getPropertiesFile() {
		return traFile;
	}
}
