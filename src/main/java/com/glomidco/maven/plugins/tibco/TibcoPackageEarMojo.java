package com.glomidco.maven.plugins.tibco;

import java.io.File;
import java.util.Collection;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

import com.glomidco.maven.plugins.tibco.command.DesignerCommandException;
import com.glomidco.maven.plugins.tibco.command.EarBuilderCommand;
import com.glomidco.maven.plugins.tibco.command.LibBuilderCommand;

/**
 * This class builds the Tibco BusinessWorks .ear
 * 
 * @author mark
 *
 * @requiresProject true
 * @goal package-ear
 */
public class TibcoPackageEarMojo extends AbstractPackageMojo {
	/**
	 * The maven project
	 * 
	 *  @parameter property="project"
	 *  @required
	 *  @read-only
	 */
	private MavenProject project;
	
	/**
	 * Directory and filename of the buildear executable
	 * 
	 * @parameter property="buildArchiveCommand"
	 */
	private String buildArchiveCommand;
	
	/**
	 * Directory and filename of the buildear properties file
	 * 
	 * @parameter property="buildArchiveTraFile"
	 */
	private String buildArchiveTraFile;

	public void execute() throws MojoExecutionException, MojoFailureException {
		Collection<File> archiveResources = findArchiveResources();

		for (File archiveResource : archiveResources) {
			createTibcoArchive(archiveResource);
		}
	}
	
	private void createTibcoArchive(File archiveResource) throws MojoExecutionException {
    	getLog().info("Creating project archive for: " + archiveResource.getName());
    	
    	String uri = archiveResource.getAbsolutePath().substring(getTibcoBuildDirectory().getAbsolutePath().length());
    	File artifactName = new File(getOutputDirectory(), project.getArtifact().getArtifactId() + "-" + project.getArtifact().getBaseVersion() + ".ear");
    	
    	EarBuilderCommand command = new EarBuilderCommand(buildArchiveCommand, buildArchiveTraFile, getTibcoBuildDirectory(), uri, artifactName, null);
    	try {
    		command.execute();
    	} catch (DesignerCommandException e) {
    		throw new MojoExecutionException("Can't create project archive", e);
    	}
	}
	
	private Collection<File> findArchiveResources() throws MojoExecutionException {
		return findTibcoBuilderResources(".archive");
	}
}
