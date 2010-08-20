package com.curlap.orb.plugin.builder;

import java.util.Map;

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
import org.eclipse.jdt.internal.core.JavaElement;
import org.eclipse.jface.preference.IPreferenceStore;

import com.curlap.orb.plugin.OrbPlugin;
import com.curlap.orb.plugin.ui.PreferenceConstants;

public class CurlOrbBuilder extends IncrementalProjectBuilder {

	public static final String BUILDER_ID = "com.curlap.orb.plugin.orbbuilder";
	private IProject fCurlProject;
	
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
	    getProject().accept(new IResourceVisitor() {
	        public boolean visit(IResource resource) throws CoreException {
	            // build processing 
	        	if(resource instanceof IFile) {
	
	        	}
	            return true;
	        }
	    });
	}

	private void incrementalBuild(IResourceDelta delta, IProgressMonitor monitor)
	        throws CoreException {
	    delta.accept(new IResourceDeltaVisitor() {
	        public boolean visit(IResourceDelta delta) throws CoreException {
	            // build processing
	        	if(delta instanceof IFile) {
	        		
	        	}
	            return true;
	        }
	    });
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
    	boolean selected 
    		= store.getBoolean(
    			PreferenceConstants.CURL_ORB_BUILDER_ENABLED);
        
        //Å@Java project
    	String javaPrjName = "";  //$NON-NLS-1$
    	javaPrjName = store.getString(PreferenceConstants.CURL_ORB_BUILDER_JAVA_PRJNAME);
    	    	
    	// Curl project
    	String curlPrjName = "";  //$NON-NLS-1$
    	curlPrjName = store.getString(PreferenceConstants.CURL_ORB_BUILDER_CURL_PRJNAME);
    	
    	IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
    	fCurlProject = root.getProject(curlPrjName);
    	
    }

	@Override
	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) throws CoreException {
		setCurlProject();
		super.setInitializationData(config, propertyName, data);
	}
}
