package com.glomidco.maven.plugins.util;

import java.io.File;
import java.io.IOException;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.codehaus.plexus.util.FileUtils;

import com.glomidco.maven.plugins.tibco.Messages;

public class PluginUtils {

	/**
	 * Delete a file, handling log messages and exceptions
	 * 
	 * @param f
	 *            File to be deleted
	 * @throws MojoExecutionException
	 *             only if a file exists and can't be deleted
	 */
	public static void delete(File f, Log log) throws MojoExecutionException {
		if (f.isDirectory()) {
			log.info(Messages.getString(
					"TibcoPluginUtils.deletingDirectory", f.getName()));
		} else {
			log.info(Messages.getString(
					"TibcoPluginUtils.deletingFile", f.getName()));
		}

		if (f.exists()) {
			if (!f.delete()) {
				try {
					FileUtils.forceDelete(f);
				} catch (IOException e) {
					throw new MojoExecutionException(Messages.getString(
							"TibcoPluginUtils.failedtodelete",
							new Object[] { f.getName(), f.getAbsolutePath() }));
				}
			}
		} else {
			log.debug(Messages.getString(
					"TibcoPluginUtils.nofilefound", f.getName()));
		}
	}

}
