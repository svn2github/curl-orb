package com.curlap.orb.plugin.ui;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

public class CurlOrbUIPreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
    public void initializeDefaultPreferences() {
		IPreferenceStore store = PreferenceConstants.getPreferenceStore();
		PreferenceConstants.initializeDefaultValues(store);
	}	
}