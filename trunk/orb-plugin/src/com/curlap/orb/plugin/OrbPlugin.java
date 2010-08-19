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

package com.curlap.orb.plugin;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.apache.velocity.app.Velocity;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class OrbPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.curlap.orb.plugin";

	// The shared instance
	private static OrbPlugin plugin;
	
	/**
	 * The constructor
	 */
	public OrbPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		initVelocity();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static OrbPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	public Path getPluginPath() {
        URL url = getBundle().getEntry("/"); //$NON-NLS-1$
        try {
            url = FileLocator.toFileURL(url);
        } catch (IOException e) {
            return null;
        }
        return new Path(url.getPath());
	}
	
	private void initVelocity(){
		Path rootPath = getPluginPath();
		String propsPath = rootPath.append("velocity.properties").toString();
		try {
			// load property file.
			FileInputStream propsStream = new FileInputStream(propsPath);
			Properties props = new Properties();
			props.load(propsStream);
			
			// append property to set the base path used by the file loader.
			props.setProperty("file.resource.loader.path", rootPath.toString());
			Velocity.init(props);			
		} catch (Exception ex) {
			ex.printStackTrace();
			//TODO: handle exception
		}
	}
}
