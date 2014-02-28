package com.glomidco.maven.plugins.tibco;

import java.util.Collection;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * This class builds the Tibco BusinessWorks .ear
 * 
 * @author mark
 *
 * @requiresProject true
 * @goal package-ear
 */
public class TibcoPackageEarMojo extends AbstractPackageMojo {
	public void execute() throws MojoExecutionException, MojoFailureException {
		Collection<String> archiveResources = findArchiveResources();

		for (String archiveResource : archiveResources) {
			createTibcoArchive(archiveResource);
		}
	}
	
	private void createTibcoArchive(String archiveResource) {
    	getLog().info("Creating project archive for: " + archiveResource);
	}
	
	private Collection<String> findArchiveResources() throws MojoExecutionException {
		return findTibcoBuilderResources(".archive");
	}
}
