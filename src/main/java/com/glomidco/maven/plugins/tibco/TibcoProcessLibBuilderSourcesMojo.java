package com.glomidco.maven.plugins.tibco;

import java.io.File;
import java.io.IOException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.FileUtils;

/**
 * 
 * @author mark
 *
 * @requiresProject = true
 * @goal process-sources
 */
public class TibcoProcessLibBuilderSourcesMojo extends AbstractMojo {
	/**
	 * The Maven project
	 * 
	 * @parameter property="project"
	 * @required
	 * @readonly
	 */
	protected MavenProject project;
	
	/**
     * Directory containing the processes and resource files that should be packaged 
     * into the project library.
     *
     * @parameter default-value="${project.build.outputDirectory}
     * @required
	 * @readonly
     */
    private File tibcoBuildDirectory;

    /**
     * Directory containing the generated PROJLIB.
     *
     * @parameter default-value="${project.build.directory}
     * @required
	 * @readonly
     */
    private File outputDirectory;

    @Override
	public void execute() throws MojoExecutionException, MojoFailureException {
    	File sourceDirectory = project.getBasedir().getAbsoluteFile();
    	File destinationDirectory = tibcoBuildDirectory;
    	
    	getLog().info("Copying sources from " + sourceDirectory.getAbsolutePath() + " to " + destinationDirectory.getAbsolutePath());
    	
    	try {
        	FileUtils.copyDirectoryStructure(sourceDirectory, destinationDirectory);
    	} catch(IOException e) {
    		throw new MojoExecutionException("Can't copy sources to: " + destinationDirectory);
    	}
	}

}
