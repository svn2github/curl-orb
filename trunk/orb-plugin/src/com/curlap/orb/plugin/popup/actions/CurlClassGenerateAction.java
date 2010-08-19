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

/**
 * Curl specification utility.
 * 
 * @author Wang Huailiang
 * @since 0.8
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

public class CurlClassGenerateAction implements IObjectActionDelegate {

	private Log log;
	private Shell shell;
	private ICompilationUnit source;
	private String completeStatus = "Initial...";
	
	/**
	 * Constructor for Action1.
	 */
	public CurlClassGenerateAction() {
		super();
		this.log = LogFactory.getLog(getClass());
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
		/*
		 * public static void openInformation(Shell parent,
                                   String title,
                                   String message)

    Convenience method to open a standard information dialog.

    Parameters:
        parent - the parent shell of the dialog, or null if none
        title - the dialog's title, or null if none
        message - the message


		 * 
		 */
		
		String generateDate;
		
		try {
			CurlClassGenerator generator = CurlClassGeneratorFactory.getInstance().createGenerator(source);
			if (generator == null)
				return;
			
			VelocityContext context = generator.generateClass();
			
			generateDate = SimpleDateFormat.getInstance().format(new Date());
			context.put("generateDate",generateDate);
			
			System.out.println(String.valueOf(context.get("completeStatus")));
			if( String.valueOf(context.get("completeStatus")) != "null")
				completeStatus = String.valueOf(context.get("completeStatus") );
			else
				completeStatus = "Initialization problem!"; 
			
	//write Curl source code file
			File file = null;
			FileWriter fileWriter = null;
			try {
				//Get the output file name from the context
				file = new File(context.get("className")+".scurl");
				fileWriter = new FileWriter(file);
            
				Template template = Velocity.getTemplate(generator.getVelocityTemplateName());
				template.merge(context, fileWriter);
            
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e){
				log.debug(e.getMessage());
			} finally {
				if (fileWriter != null)
					fileWriter.close();
			}
      

   //create or rewrite load file     
            File load = new File(generator.getPackageFileName());
            FileWriter loadFileWriter = null;
            BufferedReader br = null;
            FileWriter loadFileReWriter = null;
            
            try {
            	if (!load.exists()){ // whether the load file already exists ?
            		loadFileWriter = new FileWriter(load);
    	          	
    	           VelocityContext contextPackageFile = new VelocityContext();
    	           contextPackageFile.put("packageName4Curl",generator.getPackageName().toUpperCase() );
    	           contextPackageFile.put("fileUrl", generator.getFileName());
    	           
     	           Template packageFileTemplate = Velocity.getTemplate(generator.getPackageVelocityTemplateName());
     	          packageFileTemplate.merge(contextPackageFile, loadFileWriter);
     	          loadFileWriter.flush();
     	            
    	         } else {
    	        	log.debug("Load file Already exits!");
    	            FileReader in = new FileReader(load);
    	            br = new BufferedReader(in);
    	            String line;
    	            Boolean included = false;
    	            while ((line = br.readLine()) != null) {
    	            	log.debug(line);
    	                if(line.indexOf("\"" + generator.getFileName() + "\"") != -1)
    	                	included = true;
    	            }
    	            br.close();
    	            in.close();
    	            
    	           	if(!included){
    	           		loadFileReWriter = new FileWriter(load, true);
    	           		loadFileReWriter.write("\n{include \""+generator.getFileName()+"\"}");
    	           		loadFileReWriter.flush();
    	           		loadFileReWriter.close();
    	           	}
    	         }
            } catch (IOException e){
            	log.debug(e.getMessage());
            } finally {
            	if (br != null)
            		br.close();
            	if (loadFileWriter != null)
            		loadFileWriter.close();
            	if (loadFileReWriter != null)
            		loadFileReWriter.close();
			}
            

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.debug("");
		log.debug("==================End=================");
		log.debug("");
		
		
		MessageDialog.openInformation(
			shell,
			"ORB",
			completeStatus);
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		
		IStructuredSelection structedSelection = (IStructuredSelection)selection;
		ICompilationUnit iCompilationUnit  = (ICompilationUnit)structedSelection.getFirstElement();
		if (iCompilationUnit == null)
			;
		else
			this.source = iCompilationUnit;
	}
	
	

}
