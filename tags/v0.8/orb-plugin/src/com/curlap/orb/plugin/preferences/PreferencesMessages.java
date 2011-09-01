package com.curlap.orb.plugin.preferences;

import org.eclipse.osgi.util.NLS;


public final class PreferencesMessages extends NLS {


        private static final String BUNDLE_NAME = "com.curlap.orb.plugin.preferences.PreferencesMessages"; //$NON-NLS-1$

	static {
	    NLS.initializeMessages(BUNDLE_NAME, PreferencesMessages.class);
	}

	private PreferencesMessages() {
		// Do not instantiate
	}

    public static String NewSourceFolderWizardPage_ChooseProjectDialog_title;
    public static String NewSourceFolderWizardPage_ChooseProjectDialog_description;
    public static String NewSourceFolderWizardPage_project_label;
	public static String NewSourceFolderWizardPage_project_button;
	
    public static String CurlOrbGeneralPreferencesPage_Heading;
	public static String CurlOrbGeneralPreferencesPage_enable_builder;
	
	public static String LaunchAppletConfigurationMainTab_0;
	public static String LaunchAppletConfigurationMainTab_1;
	
}
