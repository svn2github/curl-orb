package com.curlap.orb.plugin.preferences;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

/**
 * Base class for all Curl preference pages.
 */
public abstract class CurlOrbPreferencesPage extends PreferencePage implements
        IWorkbenchPreferencePage
{
    public final String fPageHelpID;
    
    /**
     * 
     */
    public CurlOrbPreferencesPage(String helpID)
    {
        super();
        fPageHelpID = helpID;
    }

    /**
     * @param title
     */
    public CurlOrbPreferencesPage(String title, String helpID)
    {
        super(title);
        fPageHelpID = helpID;
    }

    /**
     * @param title
     * @param image
     */
    public CurlOrbPreferencesPage(
            String title,
            ImageDescriptor image,
            String  helpID)
    {
        super(title, image);
        fPageHelpID = helpID;
    }

    @Override
    protected Control createContents(Composite parent)
    {
        initializeDialogUnits(parent);
        noDefaultAndApplyButton();
        final Composite mainComposite = new Composite(parent, SWT.NONE);
        mainComposite.setFont(parent.getFont());
        mainComposite.setLayout(new GridLayout());
        mainComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

        createControlContent(mainComposite);
        Dialog.applyDialogFont(mainComposite);
        setValid(true);
        return mainComposite;
    }

    protected abstract void createControlContent(Composite mainComposite);

    @Override
    public void createControl(Composite parent)
    {
        super.createControl(parent);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), fPageHelpID);
    }
    
    @Override
    public void init(IWorkbench workbench)
    {
        // Nothing to do.
    }
    
    protected void showDefaultMessage() {
        setMessage("CurlORB"); //$NON-NLS-1$
    }
}
