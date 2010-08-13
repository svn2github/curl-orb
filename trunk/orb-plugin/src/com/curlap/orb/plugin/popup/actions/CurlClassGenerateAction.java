package com.curlap.orb.plugin.popup.actions;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IImportDeclaration;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageDeclaration;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.curlap.org.plugin.bean.Field;

public class CurlClassGenerateAction implements IObjectActionDelegate {

	private Shell shell;
	
	/**
	 * Constructor for Action1.
	 */
	public CurlClassGenerateAction() {
		super();
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
		MessageDialog.openInformation(
			shell,
			"OG",
			"New Action was executed.");
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		
		ArrayList<String> imports_list = new ArrayList<String>();
		ArrayList<String> package_list = new ArrayList<String>();
		ArrayList<Field> field_list = new ArrayList<Field>();
		
		
		ArrayList<String> skip_package_list = new ArrayList<String>();

		String generate_date = SimpleDateFormat.getInstance().format(new Date());
		
	
		String file_url="test.scurl";
		
		String s;	//temp 

		//skip package instances 
		skip_package_list.add("COM.CURL.IO.SERIALIZE.TYPES");
		skip_package_list.add("JAVA.LANG");
		skip_package_list.add("JAVA.UTIL");
		skip_package_list.add( "JAVA.SQL");
		skip_package_list.add("JAVA.MATH");
		
		System.out.println("================Start==========================");
		//System.out.println("selection.toString()");
		//System.out.println(selection.toString());
		//System.out.println("MMMMMMMMMMMMMMMMMMMMM");
		//System.out.println("((IStructuredSelection)selection).getFirstElement().getClass().getName()");
		//System.out.println(((IStructuredSelection)selection).getFirstElement().getClass().getFields());
		//System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAA");
		IStructuredSelection structedSelection = (IStructuredSelection)selection;
		ICompilationUnit source = (ICompilationUnit)structedSelection.getFirstElement();
		
		
		try {
			
			// Velocity.init("velocity.properties");
	        // VelocityContext context = new VelocityContext();
			
			//IJavaElement elements[] = source.getChildren();

			//Package 
			System.out.println("Package");
			IPackageDeclaration[] packageDeclarations = source.getPackageDeclarations();
			for (IPackageDeclaration iPackageDeclaration : packageDeclarations){
				
				s = iPackageDeclaration.getElementName();
				if(iPackageDeclaration != null){
		        	 package_list.add(s);
		        }
				
			}
			
			for(String st :package_list){
		    	System.out.println(st);
		    }
			System.out.println("=====================");
			
			//Add the packages user defined 
			for(String st :package_list){
				skip_package_list.add(st.toUpperCase());
		    }
			
			//Imports 
			System.out.println("Imports");
		    IImportDeclaration[] imports = source.getImports();
		    for (IImportDeclaration iImportDeclaration : imports){
				s = iImportDeclaration.getElementName();
				if(s != null){
		        	 imports_list.add(s);
		        }
				
			}
		    for(String st :imports_list){
		    	System.out.println(st);
		    }
		    
		    System.out.println("Generate imports");
		    for(String st :imports_list){
		    	/*
		    	{if ip != null and {ip.prefix? "COM.CURLAP.ORB."} then
		            {continue} || COM.CURLAP.ORB.XXX -> COM.CURLAP.ORB
		        }
		        {if self.as-template? and {ip.prefix? "COM.CURLAP.ORB"} then
		            {continue} || Not add COM.CURLAP.ORB to template class.
		        }
		        */
		    	Boolean in_skip = false;
		    	for(String str :skip_package_list){
			    	if(st.toUpperCase().indexOf(str) != -1)
			    		in_skip = true;
			    }
		    	
		    	if(in_skip == false)
		    		System.out.println(st);
		    }
		    
		    
		    System.out.println("=====================");
		    IType [] types = source.getTypes();
		   
		    for(IType itype : types){
		    	IJavaElement[] ee = itype.getChildren();
		    	System.out.println("****************");
		    	for(IJavaElement e:ee){
		    		System.out.println(e.getElementName());
		    	}
		    	System.out.println("****************");
		    	
		    	System.out.println("Fields");
		    	IField[] fields = itype.getFields();
		    	for(IField field:fields){
		    		System.out.print(field.getElementName());
		    		System.out.print("\t"+Flags.toString(field.getFlags()));
		    		System.out.println("\t"+field.getTypeSignature());
		    		
		    	}
		    	
		    	System.out.println("Generate field");
		    	Field f = new Field();
		    	for(IField field:fields){
		    		f.setField_name(field.getElementName());
		    		f.setField_publicity(Flags.toString(field.getFlags()));
		    		f.setField_type(field.getTypeSignature());
		    		if(Flags.isStatic(field.getFlags()))
		    			f.setField_is_static("let");
		    		else
		    			f.setField_is_static("field");
		    		field_list.add(f);
		    		
		    	}
		    	System.out.println(field_list.size());
		    	
		    	System.out.println("Generate fields");
		    	for(Field fi:field_list){
		    		
		    	}
		    	System.out.println("****************");
		    	
		    	IMethod[] methods = itype.getMethods();
		    	for(IMethod method:methods){
		    		System.out.println(method.getElementName());
		    		System.out.println(method.getReturnType());
		    		System.out.println(method.getParameterNames());
		    	}
		    }
		    System.out.println("=====================");
		    
			System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWW");

	       
			
/*			context.put("package_name", package_list);
			context.put("generate_date", generate_date);
			context.put("imports_list", imports_list);
			context.put("field_list", field_list);
			
			File file = new File(file_url);
            FileWriter fileWriter = new FileWriter(file);
            
            Template template = Velocity.getTemplate("templates/test.vm");
            
            template.merge(context, fileWriter);
            
            fileWriter.flush();
*/
			//System.out.println(source.getSource());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("==================End======dd===========");
		
		//source.getTypes();
	}

}
