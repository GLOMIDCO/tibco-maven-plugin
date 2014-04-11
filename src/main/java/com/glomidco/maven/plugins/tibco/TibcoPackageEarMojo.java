package com.glomidco.maven.plugins.tibco;

import java.io.File;
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
		Collection<File> archiveResources = findArchiveResources();

		for (File archiveResource : archiveResources) {
			createTibcoArchive(archiveResource);
		}
	}
	
	private void createTibcoArchive(File archiveResource) {
    	getLog().info("Creating project archive for: " + archiveResource.getName());
	}
	
	private Collection<File> findArchiveResources() throws MojoExecutionException {
		return findTibcoBuilderResources(".archive");
	}
}
