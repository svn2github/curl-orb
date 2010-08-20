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
import com.curlap.orb.plugin.ui.PreferenceConstants;

public class CurlOrbBuilder extends IncrementalProjectBuilder {

	public static final String BUILDER_ID = "com.curlap.orb.plugin.orbbuilder";
	private IProject fCurlProject;
	private URI fCurlProjectLocation;
	
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

	private void incrementalBuild(IResourceDelta delta, IProgressMonitor monitor)
	        throws CoreException {
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
	        		if(element != null && element instanceof ICompilationUnit) {
  			
	        			generateCurlFiles((ICompilationUnit)element);
	        		}
	        		
	        	}
	        	
	            return true;
	        }
	    });
	}
	
	private void generateCurlFiles(ICompilationUnit source){
		try {
			CurlClassGenerator generator = 
				CurlClassGeneratorFactory.getInstance().createGenerator(source);
			if (generator == null)
				return;

			VelocityContext context = generator.generateClass();
			context.put(
					"generate_date", SimpleDateFormat.getInstance().format(new Date())
			);

			System.out.println(String.valueOf(context.get("completeStatus")));
		/*	if (String.valueOf(context.get("completeStatus")) != "null")
				completeStatus = String.valueOf(context.get("completeStatus") );
			else
				completeStatus = "Initialization problem!"; */

			// write Curl source code file
			FileWriter fileWriter = null;
			try {
				// Get the output file name from the context
				if(generator.getSavePath() == null)
					fileWriter = new FileWriter(new File(fCurlProjectLocation.getPath() + "\\" + generator.getFileName()));
				else
					fileWriter = new FileWriter(new File(fCurlProjectLocation.getPath() +generator.getSavePath() + "\\" + generator.getFileName()));
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
			File load = null;
			
/*			if(generator.getSavePath() == null)
				load = new File(fCurlProjectLocation.getPath() + "\\" +generator.getPackageFileName());
			else
				load = new File(fCurlProjectLocation.getPath() + generator.getSavePath() + "\\" +generator.getPackageFileName());
			
*/			load = new File(fCurlProjectLocation.getPath() + "\\" +generator.getPackageFileName());
			FileWriter loadFileWriter = null;
			BufferedReader br = null;
			FileWriter loadFileReWriter = null;
			try {
				if (!load.exists()) { // whether the load file already exists ?
					loadFileWriter = new FileWriter(load);
					VelocityContext contextPackageFile = new VelocityContext();
					contextPackageFile.put(
							"packageName4Curl", 
							generator.getPackageName().toUpperCase()
					);
					contextPackageFile.put(
							"fileUrl",
							((generator.getSavePath() == null)?generator.getFileName():generator.getSavePath() + "\\" +generator.getFileName())
					);

					Template packageFileTemplate = 
						Velocity.getTemplate(generator.getPackageVelocityTemplateName());
					packageFileTemplate.merge(contextPackageFile, loadFileWriter);
					loadFileWriter.flush();
				} else {
					FileReader in = new FileReader(load);
					br = new BufferedReader(in);
					String line;
					boolean included = false;
					String saveFileName = (generator.getSavePath() == null)?generator.getSavePath():generator.getSavePath() + "/" + generator.getFileName();
					while ((line = br.readLine()) != null) {
						if (line.indexOf("\"" + saveFileName + "\"") != -1)
							included = true;
					}
					br.close();
					in.close();
					if (!included) {
						loadFileReWriter = new FileWriter(load, true);
						loadFileReWriter.write("\n{include \"" + saveFileName + "\"}");
						loadFileReWriter.flush();
						loadFileReWriter.close();
					}
				}
			} catch (IOException e) {
				throw new CurlGenerateException(e);
			} finally {
				if (br != null)
					br.close();
				if (loadFileWriter != null)
					loadFileWriter.close();
				if (loadFileReWriter != null)
					loadFileReWriter.close();
			}
		} catch (Exception e) {
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
 /*   	boolean selected 
    		= store.getBoolean(
    			PreferenceConstants.CURL_ORB_BUILDER_ENABLED);
        */
  /*      //�@Java project
    	String javaPrjName = "";  //$NON-NLS-1$
    	javaPrjName = store.getString(PreferenceConstants.CURL_ORB_BUILDER_JAVA_PRJNAME);
    	  */  	
    	// Curl project
    	String curlPrjName = "";  //$NON-NLS-1$
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
