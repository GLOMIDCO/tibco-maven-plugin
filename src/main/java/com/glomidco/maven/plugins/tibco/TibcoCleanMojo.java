package com.glomidco.maven.plugins.tibco;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import com.glomidco.maven.plugins.util.PluginUtils;

/**
 * Deletes the .designtimelibs files used by Tibco
 * 
 * @author mark
 *
 * @goal clean
 */
public class TibcoCleanMojo extends AbstractMojo {
	/**
	 * Designtimelibs definition file for an Tibco Project
	 */
	private static final String FILE_DOT_DESIGNTIMELIBS = ".designtimelibs";
	
	/**
	 * The root directory of the project
	 * 
	 * @parameter property="basedir"
	 */
	private File basedir;
	
	/**
	 * Skip the operation when true
	 * 
	 * @parameter property="tibco.skip" default-value="false"
	 */
	private boolean skip;
	
	public void execute() throws MojoExecutionException {
		if (skip) {
			return;
		}

		PluginUtils.delete(new File(basedir, FILE_DOT_DESIGNTIMELIBS), getLog());
	}
}
