package com.glomidco.maven.plugins.tibco;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

/**
 * This class builds the Tibco BusinessWorks .projlib
 * 
 * @author mark
 *
 * @requiresProject true
 * @goal package
 */
//@Mojo( name = "projlib", defaultPhase = LifecyclePhase.PACKAGE, requiresProject = true, threadSafe = true,
//requiresDependencyResolution = ResolutionScope.RUNTIME )
public class TibcoLibBuilderMojo extends AbstractMojo {
	/**
	 * Name of the build folder containing the .libbuilder files
	 * 
	 */
	@Parameter(defaultValue = "/_Builds")
	private String tibcoLibBuilderFolder;

	/**
     * Directory containing the processes and resource files that should be packaged 
     * into the project library.
     * 
     */
    @Parameter(defaultValue = "${project.build.outputDirectory}", required = true)
    private File tibcoBuildDirectory;

    /**
     * Directory containing the generated PROJLIB.
     * 
     */
    @Parameter(defaultValue = "${project.build.directory}", required = true )
    private File outputDirectory;

	/**
	 * Absolute path and filename of the Designer5.prefs file (usually in ~/.TIBCO)
	 * 
	 */
    @Parameter(defaultValue = "${user.home}/.TIBCO/Designer5.prefs")
	private String designerPrefsFile;
	
	public void execute() throws MojoExecutionException, MojoFailureException {
		Collection<String> libBuilderResources = findLibBuilderResources();

		hideLibraryResources();

		for (String libBuilderResource : libBuilderResources) {
			createTibcoProjlib(libBuilderResource);
		}

		showLibraryResources();
	}
	
	private void createTibcoProjlib(String libBuilderResource) {
		
	}
	
	private void hideLibraryResources() {
		
	}
	
	private void showLibraryResources() {
		
	}
	
	private Collection<String> findLibBuilderResources() throws MojoExecutionException {
		File buildFolder = new File(tibcoBuildDirectory + File.separator + tibcoLibBuilderFolder);
		return findTibcoBuilderResources(buildFolder, ".libbuilder");
	}
	
	private Collection<String> findTibcoBuilderResources(File tibcoBuildFolder, String type) throws MojoExecutionException {
		Collection<String> buildResources = new ArrayList<String>();
		if (tibcoBuildFolder.exists() && tibcoBuildFolder.isDirectory()) {
			for (String tibcoBuildResource : tibcoBuildFolder.list()) {
				if (tibcoBuildResource.endsWith(type)) {
					buildResources.add(tibcoBuildResource);
				}
			}
		} else {
			throw new MojoExecutionException(Messages.getString("TibcoLibBuilderMojo.nolibbuilderfound", tibcoBuildFolder));
		}
		return buildResources;
	}
}
