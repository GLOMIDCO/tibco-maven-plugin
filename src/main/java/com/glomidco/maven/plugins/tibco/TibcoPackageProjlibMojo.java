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
 * @goal package-projlib
 */
public class TibcoPackageProjlibMojo extends AbstractPackageMojo {
	public void execute() throws MojoExecutionException, MojoFailureException {
		Collection<String> libBuilderResources = findLibBuilderResources();

		hideLibraryResources();

		for (String libBuilderResource : libBuilderResources) {
			createTibcoProjlib(libBuilderResource);
		}

		showLibraryResources();
	}
	
	private void hideLibraryResources() {
		getLog().info("Hiding library resources");
	}
	
	private void showLibraryResources() {
		getLog().info("Showing library resources");
	}

	private void createTibcoProjlib(String libBuilderResource) {
    	getLog().info("Creating project library for: " + libBuilderResource);
	}
	
	private Collection<String> findLibBuilderResources() throws MojoExecutionException {
		return findTibcoBuilderResources(".libbuilder");
	}

}
