package com.glomidco.maven.plugins.tibco;

import java.io.File;
import java.util.Collection;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

import com.glomidco.maven.plugins.tibco.command.DesignerCommandException;
import com.glomidco.maven.plugins.tibco.command.LibBuilderCommand;

/**
 * This class builds the Tibco BusinessWorks .ear
 * 
 * @author mark
 *
 * @requiresProject true
 * @goal package-projlib
 */
public class TibcoPackageProjlibMojo extends AbstractPackageMojo {
	/**
	 * The maven project
	 * 
	 *  @parameter property="project"
	 *  @required
	 *  @read-only
	 */
	private MavenProject project;

	/**
	 * Directory which will contain the build project library
	 * 
	 * @parameter default-value="${project.build.directory}"
	 * @required
	 */
	private File buildDirectory;
	
	public void execute() throws MojoExecutionException, MojoFailureException {
		Collection<File> libBuilderResources = findLibBuilderResources();

		for (File libBuilderResource : libBuilderResources) {
			createTibcoProjlib(libBuilderResource);
		}
	}

	private void createTibcoProjlib(File libBuilderResource) throws MojoExecutionException {
    	getLog().info("Creating project library for: " + libBuilderResource.getAbsolutePath());
    	
    	String workingDir = project.getBasedir().getAbsolutePath();
    	String uri = libBuilderResource.getAbsolutePath().substring(getTibcoBuildDirectory().getAbsolutePath().length());
    	
    	LibBuilderCommand command = new LibBuilderCommand(workingDir, uri, buildDirectory, null);
    	try {
    		command.execute();
    	} catch (DesignerCommandException e) {
    		throw new MojoExecutionException("Can't create project library", e);
    	}
	}
	
	private Collection<File> findLibBuilderResources() throws MojoExecutionException {
		return findTibcoBuilderResources(".libbuilder");
	}

}
