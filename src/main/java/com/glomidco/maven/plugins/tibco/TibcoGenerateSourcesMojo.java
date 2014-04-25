package com.glomidco.maven.plugins.tibco;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.FileUtils;

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

    /**
     * Location of the Designer5.prefs (usually in ~/.TIBCO)
     * 
     * @parameter default-value="${user.home}/.TIBCO/Designer5.prefs"
     */
    private File designerPrefsFile;
    
    /**
     * Location of the fileAliases
     * 
     * @parameter default-value="${project.build.directory}/fileAliases.properties"
     */
	private File aliasesFile;
    
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		if (project.getDependencies().size() > 0) {
			generateFileAliases();
			generateDotDesignTimeLibs();
			updateDesignerPrefs();
		}
	}

	/**
	 * Generate a temporary fileAliases.properties file which can be used from within tibco to import
	 * the file aliases
	 * 
	 * @throws MojoExecutionException
	 */
	private void generateFileAliases() throws MojoExecutionException {
		if (!outputDirectory.exists()) {
			FileUtils.mkdir(outputDirectory.getAbsolutePath());
		}
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(aliasesFile));
			
			for (Artifact artifact : project.getArtifacts()) {
				String alias = getAlias(artifact);
				writer.write("tibco.alias." + alias + "=" + artifact.getFile().getAbsolutePath() + "\n");
			}
			writer.close();
		} catch (IOException e) {
			throw new MojoExecutionException("Can't create aliases file!");
		}		
	}
	
	/**
	 * Generate the .designtimelibs file with the dependencies of the pom file (and all the transitive dependencies)
	 * 
	 * @throws MojoExecutionException
	 */
	private void generateDotDesignTimeLibs() throws MojoExecutionException {
		File dotDesignTimeLibsFile = new File(project.getBasedir(), ".designtimelibs");
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(dotDesignTimeLibsFile));
			
			int index = 0;
			for (Artifact artifact : project.getArtifacts()) {
				if ("projlib".equals(artifact.getType())) {
					String alias = getAlias(artifact);
					writer.write(index++ + "=" + alias + "\\=\n");
				}
			}
			writer.close();
		} catch (IOException e) {
			throw new MojoExecutionException("Can't create .designtimelibs file!");
		}
	}
	
	/**
	 * Update the designer preference file with the preferences that don't exist
	 * 
	 * @throws MojoExecutionException
	 */
	private void updateDesignerPrefs() throws MojoExecutionException {
		getLog().info("Looking for designer prefs in: " + designerPrefsFile);
		LinkedHashMap<String, String> prefs = readDesignerPrefs();
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(designerPrefsFile, true));
			
			int index = prefs.size();
			for (Artifact artifact : project.getArtifacts()) {
					String alias = getAlias(artifact);
					if (!prefs.containsKey(alias)) {
						writer.write("filealias.pref." + index++ + "=" + alias + "\\=" + artifact.getFile().getAbsolutePath() + "\n");
					}
			}
			writer.close();
		} catch (IOException e) {
			throw new MojoExecutionException("Can't create .designtimelibs file!");
		}
	}
	
	/**
	 * Read the designer preferences
	 * 
	 * @return a map of which the key is an alias and the value is a location on disk where the alias can be found.
	 * 
	 * @throws MojoExecutionException
	 */
	private LinkedHashMap<String, String> readDesignerPrefs() throws MojoExecutionException {
		// Since the preferences can be out of order, we first read them into a map
		HashMap<Integer, String> designerPrefsMap = new HashMap<Integer, String>();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(designerPrefsFile));
			
			String line;
			Pattern pattern = Pattern.compile("filealias\\.pref\\.(\\d+?)=(.*)");
			while ((line = reader.readLine()) != null) {
				Matcher matcher = pattern.matcher(line); 
				if (matcher.find()) {
					designerPrefsMap.put(Integer.valueOf(matcher.group(1)), matcher.group(2));
				}
			}
			reader.close();
		} catch (IOException e) {
			throw new MojoExecutionException("Can't create .designtimelibs file!");
		}
		
		Pattern pattern = Pattern.compile("(.*?)\\\\=(.*)");
		LinkedHashMap<String, String> designerPrefs = new LinkedHashMap<String, String>(designerPrefsMap.size());
		for (int index = 0; index < designerPrefsMap.size(); index++) {
			Matcher matcher = pattern.matcher(designerPrefsMap.get(index));
			if (matcher.find()) {
				designerPrefs.put(matcher.group(1), matcher.group(2));
			}
		}
		return designerPrefs;
	}
	
	/**
	 * Create the alias for an maven artifact
	 * 
	 * @param artifact
	 * 
	 * @return a <code>String</code>
	 */
	private String getAlias(Artifact artifact) {
		StringBuffer buf = new StringBuffer();
		buf.append(artifact.getGroupId().replace(".", "/"));
		buf.append("/");
		buf.append(artifact.getArtifactId());
		buf.append("-");
		buf.append(artifact.getBaseVersion());
		buf.append(".");
		buf.append(artifact.getType());
		return buf.toString();
	}
}
