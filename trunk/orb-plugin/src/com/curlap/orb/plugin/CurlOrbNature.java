package com.curlap.orb.plugin;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

import com.curlap.orb.plugin.builder.CurlOrbBuilder;

public class CurlOrbNature implements IProjectNature {

	public static final String NATURE_ID = "com.curlap.orb.plugin.orbnature";
	
	public IProject project;
	
	@Override
	public void configure() throws CoreException {
		//relate project and builder
		IProjectDescription description = project.getDescription();
	    ICommand[] commands = description.getBuildSpec();
	    
	    for (ICommand command : commands) {
	        if (CurlOrbBuilder.BUILDER_ID.equals(command
	                .getBuilderName())) {
	            return;
	        }
	    }
	    
	    // add builder to project
	    ICommand[] newCommands = new ICommand[commands.length + 1];
	    System.arraycopy(commands, 0, newCommands, 0, commands.length);
	    ICommand command = description.newCommand();
	    command.setBuilderName(CurlOrbBuilder.BUILDER_ID);
	    newCommands[commands.length] = command;
	    description.setBuildSpec(newCommands);
	    project.setDescription(description, null);
	}

	@Override
	public void deconfigure() throws CoreException {
		// delete connection of builder and project
		IProjectDescription description = project.getDescription();
	    ICommand[] commands = description.getBuildSpec();
	     
	    for (int i = 0; i < commands.length; i++) {
	        if (CurlOrbBuilder.BUILDER_ID.equals(commands[i]
	                .getBuilderName())) {
	       
	            // remove builder
	            ICommand[] newCommands = new ICommand[commands.length - 1];
	            System.arraycopy(commands, 0, newCommands, 0, i);
	            System.arraycopy(commands, i + 1, newCommands, i, commands.length - i - 1);
	            description.setBuildSpec(newCommands);
	            project.setDescription(description, null);
	      
	            return;
	        }
	    }
	}

	@Override
	public IProject getProject() {
		return project;
	}

	@Override
	public void setProject(IProject project) {
		this.project = project;
	}

}
