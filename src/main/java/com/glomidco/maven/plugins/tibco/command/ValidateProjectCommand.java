package com.glomidco.maven.plugins.tibco.command;

import java.io.File;

public class ValidateProjectCommand  extends AbstractDesignerCommand {
	private String pathToAliases;
	private String traFile;
	private File projectDir;

	public ValidateProjectCommand(String command, String traFile, File projectDir, String pathToAliases) {
		super(command);
		this.traFile = traFile;
		this.projectDir = projectDir;
		this.pathToAliases = pathToAliases;
	}

	@Override
	public File getProjectFolder() {
		return projectDir;
	}

	@Override
	public String getUri() {
		return null;
	}

	@Override
	public File getOutputFile() {
		return null;
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
