package com.glomidco.maven.plugins.tibco;

import java.io.File;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

/**
 * This goal generates the file aliases for the projects.
 * 
 * @author mark
 * 
 * @goal generate-sources
 * @requiresDependencyResolution
 */
public class TibcoGenerateSourcesMojo extends AbstractMojo {
	/**
	 * The maven project
	 * 
	 *  @parameter property="project"
	 *  @required
	 *  @read-only
	 */
	private MavenProject project;

	/**
     * Directory containing the generated file aliases.
     *
     * @parameter default-value="${project.build.directory}"
     * @required
     */
    private File outputDirectory;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		if (project.getDependencies().size() > 0) {
			generateFileAliases();
		}
	}

	private boolean generateFileAliases() {
		File aliasesFile = new File(outputDirectory, "fileAliases.prefs");

		for (Artifact artifact : project.getArtifacts()) {
			String alias = artifact.getGroupId().replace(".", "/") + "/" + artifact.getArtifactId() + "-" + artifact.getBaseVersion();
			getLog().info("Alias: " + alias);
		}
		
		return true;
	}
	
	private void readAliases() {
		
	}
}
