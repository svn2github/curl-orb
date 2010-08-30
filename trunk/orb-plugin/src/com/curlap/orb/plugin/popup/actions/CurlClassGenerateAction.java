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

package com.curlap.orb.plugin.popup.actions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.curlap.orb.plugin.generator.CurlClassGenerator;
import com.curlap.orb.plugin.generator.CurlClassGeneratorFactory;
import com.curlap.orb.plugin.generator.CurlGenerateException;

/**
 * 
 * 
 * @author Wang Huailiang
 * @since 0.8
 */
public class CurlClassGenerateAction implements IObjectActionDelegate {

	private Shell shell;
	private ICompilationUnit source;

	/**
	 * Constructor for Action1.
	 */
	public CurlClassGenerateAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {

		try {
			// get proper generotor.
			CurlClassGenerator generator = 
				CurlClassGeneratorFactory.getInstance().createGenerator(source);
			if (generator == null)
				return;

			// generate
			VelocityContext context = generator.generateClass();
			context.put(
					"generate_date", SimpleDateFormat.getInstance().format(new Date())
			);

			// write Curl source code file
			FileWriter fileWriter = null;
			try {
				// Get the output file name from the context
				fileWriter = new FileWriter(new File(generator.getFileName()));
				Template template = Velocity.getTemplate(generator.getVelocityTemplateName());
				template.merge(context, fileWriter);
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				throw new CurlGenerateException(e);
			} finally {
				if (fileWriter != null)
					fileWriter.close();
			}

			// create or rewrite load file     
			File curlPackageFile = new File(generator.getPackageFileName());
			FileWriter curlPackageFileWriter = null;
			try {
				if (!curlPackageFile.exists()) {
					curlPackageFileWriter = new FileWriter(curlPackageFile);
					VelocityContext curlPackageFileContext = new VelocityContext();
					curlPackageFileContext.put(
							"package_name", 
							generator.getPackageName().toUpperCase()
					);
					curlPackageFileContext.put(
							"file_url",
							generator.getFileName()
					);
					Template packageFileTemplate = 
						Velocity.getTemplate(generator.getPackageVelocityTemplateName());
					packageFileTemplate.merge(curlPackageFileContext, curlPackageFileWriter);
					curlPackageFileWriter.flush();
					curlPackageFileWriter.close();
				} else {
					BufferedReader currentCurlPackageFileReader = null;
					FileWriter curlPackageFileReWriter = null;
					try {
						currentCurlPackageFileReader = 
							new BufferedReader(new FileReader(curlPackageFile));
						String line;
						boolean included = false;
						while ((line = currentCurlPackageFileReader.readLine()) != null) {
							if (line.indexOf("\"" + generator.getFileName() + "\"") != -1)
								included = true;
						}
						currentCurlPackageFileReader.close();
						if (!included) {
							curlPackageFileReWriter = new FileWriter(curlPackageFile, true);
							curlPackageFileReWriter.write("\n{include \"" + generator.getFileName() + "\"}");
							curlPackageFileReWriter.flush();
							curlPackageFileReWriter.close();
						}
					} catch (IOException e) {
						if (currentCurlPackageFileReader != null)
							currentCurlPackageFileReader.close();
						if (curlPackageFileReWriter != null)
							curlPackageFileReWriter.close();
					}
				}
			} catch (IOException e) {
				throw new CurlGenerateException(e);
			} finally {
				if (curlPackageFileWriter != null)
					curlPackageFileWriter.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			MessageDialog.openError(
					shell, 
					"CurlORB generator", 
					"Failed to generate Curl class."
			);
			return;
		}

		MessageDialog.openInformation(
				shell,
				"ORB",
				"Generate Curl class successfully."
		);
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		IStructuredSelection structedSelection = (IStructuredSelection)selection;
		ICompilationUnit iCompilationUnit  = (ICompilationUnit)structedSelection.getFirstElement();
		if (iCompilationUnit != null)
			this.source = iCompilationUnit;
	}
}
