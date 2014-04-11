package com.glomidco.maven.plugins.tibco.command;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import com.tibco.ae.designerapi.RootFolder;
import com.tibco.ae.tools.designer.AEDocument;
import com.tibco.ae.tools.designer.cmdline.DesignerCommandLineApp;
import com.tibco.ae.tools.designer.cmdline.TempAliases;
import com.tibco.ae.tools.palettes.projectlibrarybuilder.LibraryBuilder;
import com.tibco.ae.tools.palettes.projectlibrarybuilder.ProjectLibraryBuilderResource;
import com.tibco.tools.ArgvAssembly;
import com.tibco.tools.Token;
import com.tibco.util.ResourceManager;

abstract class AbstractDesignerCommand extends DesignerCommandLineApp implements DesignerCommand {

	public AbstractDesignerCommand() {
		ResourceManager.manager.addResource("com.tibco.ae.tools.palettes.projectlibrarybuilder.Resources");
	}

	public abstract String getProjectFilename();

	public abstract String getUri();

	public abstract File getOutputFile();

	public abstract boolean hideLibraries();

	public abstract String getPathToAliases();

	public abstract boolean getOverwriteOutputFile();

	/**
	 * 
	 * @param timeout
	 * @throws TimeoutException
	 *             execution took longer than specified in timeout
	 * @throws IOException
	 *             Could not execute command
	 */
	public void execute() throws DesignerCommandException {
		try {
			String projectFilename = getProjectFilename();

			initResources();
			initializeApp();

			TempAliases aliases = null;
			String pathToAliases = getPathToAliases();
			if (pathToAliases != null) {
				File file = new File(pathToAliases);
				if (!file.exists()) {
					throw new DesignerCommandException("ae.build.library.tool.aliases.file.doesnt.exist.message");
				}

				try {
					TempAliases tempAliases = new TempAliases();
					tempAliases.updateAliases(file);
					aliases = tempAliases;
				} catch (FileNotFoundException e) {
					throw new DesignerCommandException(e.getMessage());
				} catch (IOException e) {
					throw new DesignerCommandException(e.getMessage()); // TODO:
																		// key
																		// opzoeken
				}
			}

			Properties props = createDocumentPropertiesForFile(projectFilename);
			props.put("ae.forimport", "true");
			props.put("ae.silent", "true");

			doc = new AEDocument(props, false);

			checkForErrors();

			doc.getResourceStore().insureFoldersAreInitialized();

			checkForErrors();

			if (hideLibraries()) {
				doc.getResourceStore().setHideImports(true);
			}

			ProjectLibraryBuilderResource resource = getLibraryBuilderResource(getUri());
			if (resource == null) {
				throw new DesignerCommandException("ae.build.library.tool.library.not.found.message");
			}

			File outputFile = getOutputFile();
			if (outputFile == null) {
				throw new DesignerCommandException("ae.build.library.tool.output.file.not.specified.message");
			}

			String name = outputFile.getName();
			if (!name.endsWith(".projlib")) {
				name = (new StringBuilder()).append(name).append(".projlib").toString();
				outputFile = new File(outputFile.getParent(), name);
			}
			if (outputFile.exists() && !getOverwriteOutputFile()) {
				throw new DesignerCommandException("ae.build.library.tool.output.file.conflict.message");
			}
			if (outputFile.exists()) {
				if (!outputFile.isFile()) {
					throw new DesignerCommandException("ae.build.library.tool.output.file.folder.conflict.message");
				}
				if (outputFile.isFile() && !outputFile.delete()) {
					throw new DesignerCommandException("ae.build.library.tool.output.file.deletion.failed.message");
				}
			}
			File folders = outputFile.getParentFile();
			if (!folders.exists() && !folders.mkdirs()) {
				throw new DesignerCommandException("ae.build.library.tool.output.file.path.creation.failed.message");
			}
			try {
				resource.setProperty("fileLocation", outputFile.getCanonicalPath());

				LibraryBuilder builder = new LibraryBuilder(resource);
				builder.go(false);
			} catch (NullPointerException ne) {
				// We are expecting a NullPointerException if all goes well,
				// this is
				// because of screwed up Tibco code
				// that uses WindowManager stuff which isn't present in cmdline
				// mode. Make sure it was thrown from the
				// place we expected.
				StackTraceElement ste = ne.getStackTrace()[0];
				if (ste.getMethodName().equals("getWindowManager") && ste.getLineNumber() == 1291) {
					if (aliases != null) {
						aliases.resetAliases();
					}
				} else {
					throw new DesignerCommandException(ne);
				}
			}
		} catch (Exception e) {
			throw new DesignerCommandException(e);
		}
	}

	private ProjectLibraryBuilderResource getLibraryBuilderResource(String uri) throws DesignerCommandException {
		ProjectLibraryBuilderResource retVal = null;

		if (uri == null) {
			throw new DesignerCommandException("ae.build.library.tool.lib.flag.argument.not.specified.message");
		}
		if (!uri.startsWith("/")) {
			throw new DesignerCommandException("ae.build.library.tool.lib.flag.bad.argument.message");
		}

		RootFolder rootFolder = doc.getRootFolder();

		retVal = (ProjectLibraryBuilderResource) rootFolder.findResource(uri);

		if (retVal == null && !uri.endsWith(".libbuilder")) {
			uri = (new StringBuilder()).append(uri).append(".libbuilder").toString();
			Object test = rootFolder.findResource(uri);
			if (test != null && !(test instanceof ProjectLibraryBuilderResource)) {
				throw new DesignerCommandException("ae.build.library.tool.lib.flag.bad.reference.message");
			} else {
				retVal = (ProjectLibraryBuilderResource) test;
			}
		}
		if (retVal == null) {
			throw new DesignerCommandException("ae.build.library.tool.lib.flag.bad.reference.message");
		}
		return retVal;
	}
}
