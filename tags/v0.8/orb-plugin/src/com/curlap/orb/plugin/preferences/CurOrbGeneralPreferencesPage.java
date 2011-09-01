package com.curlap.orb.plugin.preferences;

import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaElementLabelProvider;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.curlap.orb.plugin.CurlOrbNature;
import com.curlap.orb.plugin.OrbPlugin;
import com.curlap.orb.plugin.common.CurlOrbUIIDs;
import com.curlap.orb.plugin.ui.PreferenceConstants;
import com.curlap.orb.plugin.ui.dialogfields.DialogField;
import com.curlap.orb.plugin.ui.dialogfields.IDialogFieldListener;
import com.curlap.orb.plugin.ui.dialogfields.IStringButtonAdapter;
import com.curlap.orb.plugin.ui.dialogfields.LayoutUtil;
import com.curlap.orb.plugin.ui.dialogfields.StringButtonDialogField;


/**
 * UI class to edit the general CurlORB preferences.
 */
public class CurOrbGeneralPreferencesPage extends CurlOrbPreferencesPage
{
    public static final String CURL_BASE_PREF_PAGE_ID= "com.curlap.orb.plugin.preferences.CurOrbGeneralPreferencesPage"; //$NON-NLS-1$
    
    private Button fEnableBuilder;
    private Composite fChildComposite;
    private StringButtonDialogField fJavaProjectField;
    private StringButtonDialogField fCurlProjectField;
    private IJavaProject fJavaProject;
    private IProject fCurlProject;
    private IJavaProject fJavaProjectPref;
    private IProject fCurlProjectPref;    
//    private static final int INDENT= 10;
    
    /**
     * 
     */
    public CurOrbGeneralPreferencesPage()
    {
        super(CurlOrbUIIDs.ID_CURLORB_PREFERENCES_CONTEXT_ID);
        setPreferenceStore(OrbPlugin.getDefault().getPreferenceStore());


    }
	// -------- ContainerFieldAdapter --------

	private class RootFieldAdapter implements IStringButtonAdapter, IDialogFieldListener {

		// -------- IStringButtonAdapter
		public void changeControlPressed(DialogField field) {
			packRootChangeControlPressed(field);
		}

		// -------- IDialogFieldListener
		public void dialogFieldChanged(DialogField field) {
			//packRootDialogFieldChanged(field);
		}
	}
	
	protected void packRootChangeControlPressed(DialogField field) {
		if (field == fJavaProjectField) {
			IJavaProject jproject= chooseProject();
			if (jproject != null) {
				fJavaProject = jproject;
				IPath path= jproject.getProject().getFullPath().makeRelative();
				fJavaProjectField.setText(path.toString());
			}
		} else if (field == fCurlProjectField) {
			 IProject project = chooseCurlProject();
	        if (project != null) {
	            fCurlProject = project;
	            IPath path= project.getFullPath().makeRelative();
	            fCurlProjectField.setText(path.toString());
	        }
		}
	}

	
    /**
     * chooses a project from a list of open Curl projects
     */
	private IProject chooseCurlProject() {
		ArrayList<IProject> result = new ArrayList<IProject>();
		IProject[] projects;
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
	    projects= root.getProjects();
	    for(Object obj : projects) {
	    	IProject project = (IProject)obj;
	    	try {
				if(project.hasNature("com.curl.eclipse.plugin.CurlNature")) { //$NON-NLS-1$
					result.add(project);
				}
			} catch (CoreException e) {
				OrbPlugin.log(e);
				projects= new IProject[0];
			}
	    }	    

		ILabelProvider labelProvider= new WorkbenchLabelProvider();
		ElementListSelectionDialog dialog= new ElementListSelectionDialog(getShell(), labelProvider);
		dialog.setTitle(PreferencesMessages.NewSourceFolderWizardPage_ChooseProjectDialog_title);
		dialog.setMessage(PreferencesMessages.NewSourceFolderWizardPage_ChooseProjectDialog_description);
		dialog.setElements(result.toArray()); //projects
		//dialog.setInitialSelections(new Object[] { fCurrJProject });
		dialog.setHelpAvailable(false);
		if (dialog.open() == Window.OK) {
			return (IProject) dialog.getFirstResult();
		}
		return null;
	}
	
    /**
     * chooses a project from a list of open Java projects
     */
	private IJavaProject chooseProject() {
		IJavaProject[] projects;
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		try {
			projects= JavaCore.create(root).getJavaProjects();
		} catch (JavaModelException e) {
			OrbPlugin.log(e);
			projects= new IJavaProject[0];
		}

		ILabelProvider labelProvider= new JavaElementLabelProvider(JavaElementLabelProvider.SHOW_DEFAULT);
		ElementListSelectionDialog dialog= new ElementListSelectionDialog(getShell(), labelProvider);
		dialog.setTitle(PreferencesMessages.NewSourceFolderWizardPage_ChooseProjectDialog_title);
		dialog.setMessage(PreferencesMessages.NewSourceFolderWizardPage_ChooseProjectDialog_description);
		dialog.setElements(projects);
		//dialog.setInitialSelections(new Object[] { fCurrJProject });
		dialog.setHelpAvailable(false);
		if (dialog.open() == Window.OK) {
			return (IJavaProject) dialog.getFirstResult();
		}
		return null;
	}
	
    @Override
    protected void createControlContent(
            Composite composite)
    {
        GridLayout layout = (GridLayout)composite.getLayout();
//        layout.numColumns = 2;
        layout.verticalSpacing = 10;
        
        Label heading = new Label(composite, SWT.WRAP);
        heading.setFont(composite.getFont());
        heading.setText(PreferencesMessages.CurlOrbGeneralPreferencesPage_Heading);
		
        GridData gd= new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        fEnableBuilder = new Button(composite, SWT.CHECK);
        fEnableBuilder.setText(PreferencesMessages.CurlOrbGeneralPreferencesPage_enable_builder);
        fEnableBuilder.setData(PreferenceConstants.CURL_ORB_BUILDER_ENABLED);
        fEnableBuilder.setLayoutData(gd);
        fEnableBuilder.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(
                    SelectionEvent e)
            {
                final Button btn = (Button)e.widget;
                noteEnableCheckBoxSelectionChanged(btn.getSelection());
            }
        }

        );
        
        RootFieldAdapter adapter= new RootFieldAdapter();
       
        fChildComposite= new Composite(composite, SWT.NONE);
		GridData gridData= new GridData(SWT.FILL, SWT.FILL, true, true);
//		gridData.horizontalIndent= INDENT;
		fChildComposite.setLayoutData(gridData);
		GridLayout gridLayout= new GridLayout(3, false);
		gridLayout.marginHeight= 0;
		gridLayout.marginWidth= 0;
		fChildComposite.setLayout(gridLayout);
		
		RootFieldAdapter adapterForCurl = new RootFieldAdapter();
        
		fJavaProjectField= new StringButtonDialogField(adapterForCurl );
		fJavaProjectField.setDialogFieldListener(adapterForCurl );
		fJavaProjectField.setLabelText(PreferencesMessages.NewSourceFolderWizardPage_project_label);
		fJavaProjectField.setButtonLabel(PreferencesMessages.NewSourceFolderWizardPage_project_button);
        fJavaProjectField.doFillIntoGrid(fChildComposite, 3);
        
        int maxFieldWidth= convertWidthInCharsToPixels(40);
		LayoutUtil.setWidthHint(fJavaProjectField.getTextControl(null), maxFieldWidth);
		LayoutUtil.setHorizontalGrabbing(fJavaProjectField.getTextControl(null));
		
		fCurlProjectField= new StringButtonDialogField(adapter);
		fCurlProjectField.setDialogFieldListener(adapter);
		fCurlProjectField.setLabelText(PreferencesMessages.LaunchAppletConfigurationMainTab_0);
		fCurlProjectField.setButtonLabel(PreferencesMessages.LaunchAppletConfigurationMainTab_1);
        fCurlProjectField.doFillIntoGrid(fChildComposite, 3);
		
        LayoutUtil.setWidthHint(fCurlProjectField.getTextControl(null), maxFieldWidth);
		LayoutUtil.setHorizontalGrabbing(fCurlProjectField.getTextControl(null));
		
		initState();
		restoreState();
    }	
    
    protected void noteEnableCheckBoxSelectionChanged(
            boolean selected)
    {
    	fJavaProjectField.setEnabled(selected);
    	fCurlProjectField.setEnabled(selected);
        validate();
    }
    
    private void validate()
    {
        setValid(false);
        setErrorMessage(null);
        showDefaultMessage();
        if (!validatProjectNameText()) {
            return;
        }

        setValid(true);
    }
    
    private boolean validatProjectNameText()
    {
        if (!fEnableBuilder.getSelection())
            return true;
        // TODO: check the project names, if error return false
        return true;
    }
        
    @Override
    protected void performDefaults()
    {
    	saveState();
        super.performDefaults();
    }

    @Override
    public boolean performOk()
    {
		if(fJavaProject == null){
			fJavaProject = fJavaProjectPref;
		}
		if(fCurlProject == null) {
			fCurlProject = fCurlProjectPref;
		}
		
    	if(fEnableBuilder.getSelection()) {
        	if(fJavaProject != null && fJavaProject.exists()
        	&& fCurlProject != null && fCurlProject.exists()) {
        		try {
        			if(fJavaProjectPref != null){
        			if(!fJavaProject.equals(fJavaProjectPref)){
        				if(fJavaProjectPref.getProject() != null){
        					removeNature(fJavaProjectPref.getProject());
        				}
        				//removeNature(fJavaProjectPref.getProject());
        			}
        			addNature(fJavaProject.getProject());
        			}
					
				} catch (CoreException e) {
					OrbPlugin.log(e);
				}
        	}
        } 
    	
    	saveState();    	
        return super.performOk();
    }
        
    public void saveState()
    {
    	IPreferenceStore store = getPreferenceStore();
        store.setValue(PreferenceConstants.CURL_ORB_BUILDER_ENABLED, fEnableBuilder.getSelection());        
        store.setValue(PreferenceConstants.CURL_ORB_BUILDER_JAVA_PRJNAME, fJavaProjectField.getText());
        store.setValue(PreferenceConstants.CURL_ORB_BUILDER_CURL_PRJNAME, fCurlProjectField.getText());
        
    }

    public void restoreState()
    {
    	IPreferenceStore store = getPreferenceStore();
    	boolean selected 
    		= getPreferenceStore().getBoolean(
    			PreferenceConstants.CURL_ORB_BUILDER_ENABLED);
        fEnableBuilder.setSelection(selected);
        fJavaProjectField.setEnabled(selected);
        fCurlProjectField.setEnabled(selected);
        
        //�@Java project
    	String javaPrjName = "";  //$NON-NLS-1$
    	javaPrjName = store.getString(
    		PreferenceConstants.CURL_ORB_BUILDER_JAVA_PRJNAME);
    	if(javaPrjName != null && !javaPrjName.equals("")) {
    		fJavaProjectField.setText(javaPrjName);
    		fJavaProjectPref = fJavaProject = getJavaProject(javaPrjName);
    	}		
		    	
    	// Curl project
    	String curlPrjName = "";  //$NON-NLS-1$
    	curlPrjName = store.getString(
    		PreferenceConstants.CURL_ORB_BUILDER_CURL_PRJNAME);
    	if(curlPrjName != null && !curlPrjName.equals("")) {
    		fCurlProjectField.setText(curlPrjName);
    		fCurlProjectPref = fCurlProject = getProject(curlPrjName);
    	}
    }
    
    public void initState() {
    	boolean selected = false;
    	fEnableBuilder.setSelection(selected);
    	// Java project
    	String javaPrjName = "";  //$NON-NLS-1$
    	fJavaProjectField.setText(javaPrjName);
    	fJavaProjectField.setEnabled(selected);
    	// Curl project
    	String curlPrjName = "";  //$NON-NLS-1$
    	fCurlProjectField.setText(curlPrjName);
    	fCurlProjectField.setEnabled(selected);    	
    }
    
    private IProject getProject(String prjName){
    	IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
    	return root.getProject(prjName);
    }
    
    private IJavaProject getJavaProject(String prjName){
    	IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
    	return JavaCore.create(root).getJavaProject(prjName);
    }
    
    // add nature to project
    private void addNature(IProject project) throws CoreException {
    	IProjectDescription description = project.getDescription();
    	if (!description.hasNature(CurlOrbNature.NATURE_ID)) {
			String[] natures = description.getNatureIds();
			String[] newNatures = new String[natures.length + 1];
			System.arraycopy(natures, 0, newNatures, 0, natures.length);
			newNatures[natures.length] = CurlOrbNature.NATURE_ID;
			description.setNatureIds(newNatures);
			project.setDescription(description, null);
    	}
    }
    
    // remove nature from project
    private void removeNature(IProject project) throws CoreException {
    	IProjectDescription description = project.getDescription();    	
    	if (description.hasNature(CurlOrbNature.NATURE_ID)) {
	    	String[] natures = description.getNatureIds();
	    	for (int i = 0; i < natures.length; i++) {
	    	    if (CurlOrbNature.NATURE_ID.equals(natures[i])) {
	    	        String[] newNatures = new String[natures.length - 1];
	    	        System.arraycopy(natures, 0, newNatures, 0, i);
	    	        System.arraycopy(natures, i + 1, newNatures, i, natures.length - i - 1);
	    	        description.setNatureIds(newNatures);
	    	        project.setDescription(description, null);
	    	        return;
	    	    }
	    	}
    	}
    }   

}
