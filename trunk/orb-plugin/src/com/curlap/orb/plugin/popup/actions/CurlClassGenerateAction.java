package com.curlap.orb.plugin.popup.actions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IImportDeclaration;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMemberValuePair;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageDeclaration;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.curlap.orb.plugin.common.CurlSpecUtil;
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
		ArrayList<String> method_name_list = new ArrayList<String>();
		ArrayList<String> method_name_list2 = new ArrayList<String>();
		ArrayList<String> method_param_list = new ArrayList<String>();
		ArrayList<String> method_return_type = new ArrayList<String>();
		ArrayList<String> method_arguments = new ArrayList<String>();
		
		String package_name = null;
		String class_name = null;
		String rte_version = "7.0";
		
		ArrayList<String> skip_package_list = new ArrayList<String>();

		String generate_date = SimpleDateFormat.getInstance().format(new Date());

		String file_url=".scurl";
		String file_load = "load.scurl";
		
		CurlSpecUtil tool = new CurlSpecUtil();
		
		String s;	//temp 
		
		

		//skip package instances 
		skip_package_list.add("COM.CURL.IO.SERIALIZE.TYPES");
		skip_package_list.add("JAVA.LANG");
		skip_package_list.add("JAVA.UTIL");
		skip_package_list.add( "JAVA.SQL");
		skip_package_list.add("JAVA.MATH");
		
		System.out.println("================Start==========================");

		IStructuredSelection structedSelection = (IStructuredSelection)selection;
		ICompilationUnit source = (ICompilationUnit)structedSelection.getFirstElement();
		
		
		try {
			
			//Velocity.init("velocity.properties");
	        //VelocityContext context = new VelocityContext();
	
	//Class name		
			String class_name0 = source.getElementName();
			//class_name = tool.marshalCurlName(class_name0.substring(0, class_name0.length()-5),true);
			class_name =class_name0.substring(0, class_name0.length()-5);
			
			System.out.println(class_name);

            file_url = class_name + file_url;
            
	//Package 
			System.out.println("Package");
			IPackageDeclaration[] packageDeclarations = source.getPackageDeclarations();
			for (IPackageDeclaration iPackageDeclaration : packageDeclarations){
				
				s = iPackageDeclaration.getElementName();
		        package_list.add(s);
		        package_name = s.toUpperCase();
				
			}
			
			for(String st :package_list){
		    	System.out.println(st);
		    }
			System.out.println("Package    "+ package_name);
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
					System.out.println(s);
		        	
		        }
				
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
		    		imports_list.add(st);
		    }
		    
		    
		    System.out.println("=====================");
//ITYPEs...	
		    IType [] types = source.getAllTypes();
		    for(IType itype : types){
		    	/*IJavaElement[] ee = itype.getChildren();
		    	System.out.println("****************");
		    	for(IJavaElement e:ee){
		    		System.out.println(e.getElementName());
		    	}*/
		    	System.out.println("****************");
	//Annotation
		    	System.out.println("Annotation");
		    	for(IAnnotation annotation:itype.getAnnotations()){
		    		for(IMemberValuePair pair:annotation.getMemberValuePairs()){
		    			System.out.print(pair.getMemberName());
		    			System.out.println("\t"+pair.getValue());
		    		}
		    	}
		    	System.out.println("Annotation <<<<<<<<<");
		    	
	//Fields
		    	IField[] fields = itype.getFields();
		    	//Field: name, modifier, type, 
		    	System.out.println("Generate field");
		    	
		    	for(IField field:fields){
		    		Field f = new Field();
		    		//String st = Flags.toString(field.getFlags());
		    		//String st = tool.marshalCurlName(Flags.toString(field.getFlags()),true);
		    		String st = Flags.toString(field.getFlags());


		    		if(Flags.isTransient(field.getFlags()))
		    			st = st + " transient";
		    		
		    		f.setField_name(field.getElementName());
		    		f.setField_publicity(st);
		    		f.setField_type(tool.marshalCurlType(Signature.toString(field.getTypeSignature()),true,true));
		    		if(Flags.isStatic(field.getFlags()))
		    			f.setField_is_static("let");
		    		else
		    			f.setField_is_static("field");
		    		field_list.add(f);
		    		
		    	}
		    	for(Field fie:field_list){
		    		System.out.print(fie.getField_is_static());
		    		System.out.print("\t"+fie.getField_publicity());
		    		System.out.print("\t"+fie.getField_name());
		    		System.out.print("\t"+fie.getField_type()+"\n");
		    	}
		    	System.out.println(field_list.size());
		    	

		    	System.out.println("****************");
	//Methods
		    	System.out.println("Methods");
		    	IMethod[] methods = itype.getMethods();
		    	for(IMethod method:methods){
		    		
		    		String sn = method.getElementName();
		    		//method_name_list.add(tool.marshalCurlName(sn,true));
		    		method_name_list.add(sn);
		    		method_name_list2.add(tool.marshalCurlName(sn,true));
		    		System.out.print(sn);
		    		for(IAnnotation annotation:itype.getAnnotations()){
		    			System.out.println("Method Annotation"+annotation.getElementName());
			    		for(IMemberValuePair pair:annotation.getMemberValuePairs()){
			    			System.out.print("\t"+pair.getMemberName());
			    			System.out.println("\t"+pair.getValue());
			    		}
			    	}
		    		String[] paramNames = method.getParameterNames();
		    		String[] paramTypes = method.getParameterTypes();
		    		
		    		String st = "NoParams";
		    		String st2 = "Empty";
		    		for(int i = 0;i<paramNames.length;i++){
		    			if(i==0){
		    				st = paramNames[i]+":"+tool.marshalCurlType(Signature.toString(paramTypes[i]),true,true);
		    				st2 = paramNames[i];
		    			}else{
		    				st= st + ", "+paramNames[i]+":"+tool.marshalCurlType(Signature.toString(paramTypes[i]),true,true);
		    				st2 = st2 + ", "+paramNames[i];
		    			}
		    				
		    			System.out.print("\t"+paramNames[i]);
		    			System.out.print("\t"+tool.marshalCurlType(Signature.toString(paramTypes[i]),true,true));
		    		}
		    		
		    		method_param_list.add(st);
		    		method_arguments.add(st2);
		    		System.out.println("\t"+Signature.toString(method.getReturnType()));
		    		method_return_type.add(tool.marshalCurlType(Signature.toString(method.getReturnType()),true,true));
		    	}
		    	System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<");
		    	for(int i=0;i<method_name_list.size();i++){
		    		System.out.println(method_name_list.get(i)+" "+
		    				method_param_list.get(i)+"   "+
		    				method_return_type.get(i)+"   "+
		    				method_arguments.get(i)
		    				);
		    	}
		    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>");
		    }
		    System.out.println("=====================");
		    
			System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWW");
			
			
			VelocityContext context = new VelocityContext();
			VelocityContext contextLoad = new VelocityContext();
			
	//put necessary info into context for generating the Curl source code		
			context.put("generate_date", generate_date);
			context.put("imports_list", imports_list);
			context.put("field_list", field_list);
			context.put("package_name", package_name);
			context.put("class_name",class_name );
			context.put("method_name_list", method_name_list);
			context.put("method_name_list2", method_name_list2);
			context.put("method_param_list", method_param_list);
			context.put("method_return_type",method_return_type );
			context.put("method_arguments",method_arguments );
	
	//put necessary info into context for generating the load file		
			contextLoad.put("rte_version",rte_version );
			contextLoad.put("package_name",package_name );
			contextLoad.put("file_url", file_url);
			

	//write Curl source code file		
			File file = new File(file_url);
            FileWriter fileWriter = new FileWriter(file);
            

            Template template = Velocity.getTemplate("templates/HttpSession.vm");
            template.merge(context, fileWriter);
            fileWriter.flush();
            
            System.out.println(file.getAbsolutePath());
            

      //create or rewrite load file     
            File load = new File(file_load);
	        if(!load.exists()){
	           FileWriter fileWriter2 = new FileWriter(load);
	          	
 	           Template template2 = Velocity.getTemplate("templates/load.vm");
 	           template2.merge(contextLoad, fileWriter2);
 	           fileWriter2.flush();
 	            
	         }else{
	           	System.out.println("Load file Already exits!");
	            FileReader in = new FileReader(load);
	            BufferedReader br = new BufferedReader(in);
	            String line;
	            Boolean included = false;
	            System.out.println(file_url);
	            while ((line = br.readLine()) != null) {
	            	System.out.println(line);
	                if(line.indexOf(file_url) !=-1)
	                	included = true;
	            }
	            br.close();
	            in.close();

	           	if(!included){
	           		FileWriter fileWriter2 = new FileWriter(load,true);
	           		fileWriter2.write("\n{include \""+file_url+"\"}");
		            fileWriter2.flush();
	           	}
	         }

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println();
		System.out.println("==================End=================");
		System.out.println();
		

	}

}
