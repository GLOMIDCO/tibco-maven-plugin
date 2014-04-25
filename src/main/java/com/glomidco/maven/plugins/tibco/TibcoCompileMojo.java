package com.glomidco.maven.plugins.tibco;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

import com.glomidco.maven.plugins.tibco.command.DesignerCommandException;
import com.glomidco.maven.plugins.tibco.command.ValidateProjectCommand;

/**
 * This class validates the tibco sources. The validation is done during the
 * compile fase of maven.
 * 
 * @author mark
 *
 * @goal compile
 */
public class TibcoCompileMojo extends AbstractMojo {
	/**
	 * The maven project
	 * 
	 *  @parameter property="project"
	 *  @required
	 *  @read-only
	 */
	private MavenProject project;

	/**
     * Directory containing the processes and resource files that should be packaged 
     * into the project library.
     *
     * @parameter default-value="${project.build.outputDirectory}"
     * @required
     */
    private File tibcoBuildDirectory;

    /**
     * Location of the fileAliases
     * 
     * @parameter default-value="${project.build.directory}/fileAliases.properties"
     */
	private String aliasesFile;
	
	/**
	 * Directory and filename of the validateproject executable
	 * 
	 * @parameter property="validateProjectCommand"
	 */
	private String validateProjectCommand;
	
	/**
	 * Directory and filename of the validateproject properties file
	 * 
	 * @parameter property="validateProjectTraFile"
	 */
	private String validateProjectTraFile;

	@Override
	public void execute() throws MojoExecutionException {
		validate(project);
	}

	public void validate(MavenProject project) throws MojoExecutionException {
    	getLog().info("Validating project archive for: " + project.getArtifactId());
    	
    	ValidateProjectCommand command = new ValidateProjectCommand(validateProjectCommand, validateProjectTraFile, getTibcoBuildDirectory(), aliasesFile);
    	try {
    		command.execute();
    	} catch (DesignerCommandException e) {
    		throw new MojoExecutionException("Can't validate project " + project.getArtifactId(), e);
    	}
	}

	private File getTibcoBuildDirectory() {
		return tibcoBuildDirectory;
	}

}
