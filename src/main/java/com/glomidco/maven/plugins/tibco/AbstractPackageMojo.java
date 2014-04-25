package com.glomidco.maven.plugins.tibco;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

public abstract class AbstractPackageMojo extends AbstractMojo {
	/**
	 * Name of the build folder containing the .libbuilder or .archive files
	 * 
	 * @parameter default-value="/_Builds"
	 * @required
	 */
	private String tibcoBuilderFolder;

	/**
     * Directory containing the processes and resource files that should be packaged 
     * into the project library.
     *
     * @parameter default-value="${project.build.outputDirectory}"
     * @required
     */
    private File tibcoBuildDirectory;

    /**
     * Directory containing the generated PROJLIB.
     *
     * @parameter default-value="${project.build.directory}"
     * @required
     */
    private File outputDirectory;

	/**
	 * Absolute path and filename of the Designer5.prefs file (usually in ~/.TIBCO)
	 *
	 * @parameter default-value="${user.home}/.TIBCO/Designer5.prefs"
	 */
	private String designerPrefsFile;

	protected File getTibcoBuildDirectory() {
		return tibcoBuildDirectory;
	}
	
	protected File getOutputDirectory() {
		return outputDirectory;
	}
		
	protected Collection<File> findTibcoBuilderResources(String type) throws MojoExecutionException {
		File tibcoBuildFolder = new File(getTibcoBuildDirectory(), tibcoBuilderFolder);

		Collection<File> buildResources = new ArrayList<File>();
		if (tibcoBuildFolder.exists() && tibcoBuildFolder.isDirectory()) {
			for (File tibcoBuildResource : tibcoBuildFolder.listFiles()) {
				if (tibcoBuildResource.getName().endsWith(type)) {
					buildResources.add(tibcoBuildResource);
				}
			}
		} else {
			throw new MojoExecutionException(Messages.getString("TibcoPackageMojo.nobuilderfilesfound", tibcoBuildFolder));
		}
		return buildResources;
	}

}
