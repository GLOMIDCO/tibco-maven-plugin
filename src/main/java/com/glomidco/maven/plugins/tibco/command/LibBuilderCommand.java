package com.glomidco.maven.plugins.tibco.command;

import java.io.File;

public class LibBuilderCommand extends AbstractDesignerCommand {
	private File outputLibFile;
	private String uri;
	private String pathToAliases;
	
	public LibBuilderCommand(String projectDir, String uri, File outputLibFile, String pathToAliases) {
		super();
		this.pathToAliases = pathToAliases;
//		addArgument("-p", projectDir);
		this.outputLibFile = outputLibFile;
		this.uri = uri;
	}

	@Override
	public String getProjectFilename() {
		// TODO Auto-generated method stub
		return null;
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

}
