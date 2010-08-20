package com.curlap.orb.plugin.ui;

import org.eclipse.jface.preference.IPreferenceStore;
import com.curlap.orb.plugin.OrbPlugin;

/**
 * see JDT...
 * Preference constants used in the JDT-UI preference store. Clients should only read the
 * JDT-UI preference store using these values. Clients are not allowed to modify the 
 * preference store programmatically.
 * 
 */
public class PreferenceConstants {

    private PreferenceConstants() {
    }
    
    public final static String CURL_ORB_BUILDER_ENABLED = "CurlORBBuilderEnabled"; //$NON-NLS-1$
    public final static String CURL_ORB_BUILDER_JAVA_PRJNAME = "CurlORBBuilderJavaPrjName"; //$NON-NLS-1$
    public final static String CURL_ORB_BUILDER_CURL_PRJNAME = "CurlORBBuilderCurlPrjName"; //$NON-NLS-1$

    /**
     * Initializes the given preference store with the default values.
     * 
     * @param store the preference store to be initialized
     * 
     */
    public static void initializeDefaultValues(IPreferenceStore store) {
    }

    /**
     * Returns the JDT-UI preference store.
     * 
     * @return the JDT-UI preference store
     */
    public static IPreferenceStore getPreferenceStore() {
        return OrbPlugin.getDefault().getPreferenceStore();
    }
}

