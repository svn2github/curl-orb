// Copyright (C) 1998-2010, Sumisho Computer Systems Corp. All Rights Reserved.

// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
//     http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.curlap.orb.plugin.builder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.preference.IPreferenceStore;

import com.curlap.orb.plugin.OrbPlugin;
import com.curlap.orb.plugin.generator.CurlClassGenerator;
import com.curlap.orb.plugin.generator.CurlClassGeneratorFactory;
import com.curlap.orb.plugin.generator.CurlGenerateException;
import com.curlap.orb.plugin.generator.impl.CurlServiceClassGenerator;
import com.curlap.orb.plugin.ui.PreferenceConstants;

/**
 * 
 * 
 * @author Wang Huailiang
 * @since 0.8
 */
public class CurlOrbBuilder extends IncrementalProjectBuilder {

	public static final String BUILDER_ID = "com.curlap.orb.plugin.orbbuilder";
	private IProject fCurlProject;
	private URI fCurlProjectLocation;

	@SuppressWarnings("unchecked")
	@Override
	protected IProject[] build(int kind, Map args, IProgressMonitor monitor)
	throws CoreException {
		// implement build processing
		if (kind == FULL_BUILD) {
			fullBuild(monitor);
		} else {
			IResourceDelta delta = getDelta(getProject());
			if (delta == null) {
				fullBuild(monitor);
			} else {
				incrementalBuild(delta, monitor);
			}
		}
		return null;

	}

	private void fullBuild(IProgressMonitor monitor) throws CoreException {
		setCurlProject();
		getProject().accept(new IResourceVisitor() {
			public boolean visit(IResource resource) throws CoreException {
				// build processing 
				if(resource instanceof IFile) {
					IJavaElement element = JavaCore.create(resource);
					if(element != null && element instanceof ICompilationUnit) {
						generateCurlFiles((ICompilationUnit)element);        			 
					}

				}
				return true;
			}
		});
	}

	private void incrementalBuild(IResourceDelta delta, IProgressMonitor monitor) throws CoreException {
		setCurlProject();
		delta.accept(new IResourceDeltaVisitor() {
			public boolean visit(IResourceDelta delta) throws CoreException {
				// build processing
				/*if(delta instanceof IFile) {

	        	}*/

				if(delta.getKind() == IResourceDelta.ADDED || 
						delta.getKind() == IResourceDelta.CHANGED) {
					IResource resource = delta.getResource();
					IJavaElement element = JavaCore.create(resource);
					if (element != null && element instanceof ICompilationUnit) {
						generateCurlFiles((ICompilationUnit)element);
					}

				}
				return true;
			}
		});
	}

	// create Curl source file
	private void createCurlSourceFile(
			CurlClassGenerator generator,
			VelocityContext context,
			String generatedFileName) throws CurlGenerateException
			{
		// write Curl source code file
		FileWriter fileWriter = null;
		try {
			// Get the output file name from the context
			String projectRootPath = fCurlProjectLocation.getPath();
			String filePath = 
				projectRootPath + generator.getSavePath() + "/" + generatedFileName;
			fileWriter = new FileWriter(new File(filePath));
			Template template = Velocity.getTemplate(generator.getVelocityTemplateName());
			template.merge(context, fileWriter);
			fileWriter.flush();
			fileWriter.close();
		} catch (Exception e) {
			throw new CurlGenerateException(e);
		} finally {
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException e) {
					fileWriter = null;
				}
			}
		}
			}

	private void generateCurlFiles(ICompilationUnit source){
		try {
			// create Generator
			CurlClassGenerator generator = 
				CurlClassGeneratorFactory.getInstance().createGenerator(source);
			if (generator == null)
				return;

			VelocityContext context = generator.generateClass();
			context.put(
					"generate_date",
					SimpleDateFormat.getInstance().format(new Date())
			);
			
			// create test template if needs.
			//   Note: test template is NOT added to load.scurl automatically. 
			final String testTemplatePrefix = "_test_template_";
			boolean generateTestTemplate = false;
			if (generator instanceof CurlServiceClassGenerator) {
				generateTestTemplate = (Boolean) context.get("is_template");
				if (generateTestTemplate) {
					String testTemplateFile = testTemplatePrefix + generator.getFileName();
					createCurlSourceFile(
							generator, context, testTemplateFile
					);
				}
			}

			// create Curl source
			context.put("is_template", false);
			String sourceFile = generator.getFileName();
			createCurlSourceFile(generator, context, sourceFile);

			// create or rewrite load file (Curl package file)
			File curlPackageUrl = 
				new File(fCurlProjectLocation.getPath() + "/" + generator.getPackageFileName());
			String includeUrl = 
				fCurlProjectLocation.getPath() + generator.getSavePath() + "/" + sourceFile;
			FileWriter curlPackageFileWriter = null;
			try {
				if (!curlPackageUrl.exists()) {
					curlPackageFileWriter = new FileWriter(curlPackageUrl);
					VelocityContext contextPackageFile = new VelocityContext();
					contextPackageFile.put(
							"package_name", 
							generator.getPackageName().toUpperCase()
					);
					contextPackageFile.put(
							"file_url",
							includeUrl
					);
					Template packageFileTemplate = 
						Velocity.getTemplate(generator.getPackageVelocityTemplateName());
					packageFileTemplate.merge(contextPackageFile, curlPackageFileWriter);
					curlPackageFileWriter.flush();
					curlPackageFileWriter.close();
				} else {
					BufferedReader curlPackageFileBufferedReader = null;
					try {
						FileReader curlPackageFileReader = new FileReader(curlPackageUrl);
						curlPackageFileBufferedReader = new BufferedReader(curlPackageFileReader);
						String line;
						boolean included = false;
						while ((line = curlPackageFileBufferedReader.readLine()) != null) {
							if (line.indexOf("\"" + includeUrl + "\"") != -1)
								included = true;
						}
						curlPackageFileBufferedReader.close();
						curlPackageFileReader.close();
						if (!included) {
							curlPackageFileWriter = new FileWriter(curlPackageUrl, true);
							curlPackageFileWriter.write("\n{include \"" + includeUrl + "\"}");
							curlPackageFileWriter.flush();
							curlPackageFileWriter.close();
						}
					} catch (IOException e)
					{
						if (curlPackageFileBufferedReader != null)
							curlPackageFileBufferedReader.close();
					}
				}
			} catch (IOException e) {
				throw new CurlGenerateException(e);
			} finally {
				if (curlPackageFileWriter != null)
					curlPackageFileWriter.close();
			}
		} catch (Exception e) {
			// FIXME:
			e.printStackTrace();
		}
	}

	@Override
	protected void clean(IProgressMonitor monitor) throws CoreException {
		// clean processing
	}

	@Override
	protected void startupOnInitialize() {
		// init processing
	}

	private void setCurlProject() {
		IPreferenceStore store = OrbPlugin.getDefault().getPreferenceStore(); 
		String curlPrjName = "";
		curlPrjName = store.getString(PreferenceConstants.CURL_ORB_BUILDER_CURL_PRJNAME);
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		fCurlProject = root.getProject(curlPrjName);
		fCurlProjectLocation = fCurlProject.getLocationURI();
	}

	@Override
	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) throws CoreException {
		setCurlProject();
		super.setInitializationData(config, propertyName, data);
	}
}
