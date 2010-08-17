package com.curlap.orb.plugin.popup.actions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IImportDeclaration;
import org.eclipse.jdt.core.IMemberValuePair;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageDeclaration;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.curlap.orb.plugin.common.CurlSpecUtil;
import com.curlap.orb.plugin.bean.CurlClassComponentsGen;
import com.curlap.orb.plugin.bean.Field;
import com.curlap.orb.plugin.bean.Method;

public class CurlClassGenerateAction implements IObjectActionDelegate {

	private Log log;
	private Shell shell;
	
	/**
	 * Constructor for Action1.
	 */
	public CurlClassGenerateAction() {
		super();
		this.log = LogFactory.getLog(getClass());
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
			"Curl source code is generated!");
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		
		List<String> importsList = new ArrayList<String>();
		List<String> packageList = new ArrayList<String>();
		List<Field> fieldsList = new ArrayList<Field>();
		List<String> methodNameList = new ArrayList<String>();
		List<String> methodNameList4Curl = new ArrayList<String>();
		List<String> methodParamList = new ArrayList<String>();
		List<String> methodReturnTypeList = new ArrayList<String>();
		List<String> methodArguments4Curl = new ArrayList<String>();
		List<Method> methodsList = new ArrayList<Method>();
		
		String packageName = null;
		String packageName4Curl = null;
		String className = null;
		String rteVersion = "7.0";
		
		
		String fileUrl = ".scurl";
		String loadFileUrl = "load.scurl";
		String generateDate;
		
		try {
			IStructuredSelection structedSelection = (IStructuredSelection)selection;
			ICompilationUnit source = (ICompilationUnit)structedSelection.getFirstElement();
			
	/*	List<String> skipPackageList = new ArrayList<String>();

		String generateDate = SimpleDateFormat.getInstance().format(new Date());

		String fileUrl = ".scurl";
		String loadFileUrl = "load.scurl";
		
		String s;	//temp 
		
		

		//skip package instances 
		skipPackageList.add("COM.CURL.IO.SERIALIZE.TYPES");
		skipPackageList.add("JAVA.LANG");
		skipPackageList.add("JAVA.UTIL");
		skipPackageList.add("JAVA.SQL");
		skipPackageList.add("JAVA.MATH");
		
		log.debug("================Start==========================");

		IStructuredSelection structedSelection = (IStructuredSelection)selection;
		ICompilationUnit source = (ICompilationUnit)structedSelection.getFirstElement();
		
		
		try {
			
	//Class name		
			s = source.getElementName();
			className = s.substring(0, s.length()-5);
			
			log.debug(className);

            fileUrl = className + fileUrl;
            
	//Package 
            log.debug("Package");
			IPackageDeclaration[] packageDeclarations = source.getPackageDeclarations();
			for (IPackageDeclaration iPackageDeclaration : packageDeclarations){
				
				s = iPackageDeclaration.getElementName();
		        packageList.add(s);
		        
		        packageName = s;
		        packageName4Curl = packageName.toUpperCase();
				
			}
			
			for(String st :packageList){
				log.debug(st);
		    }
			log.debug("Package    "+ packageName);
			log.debug("=====================");
			
	//Add the packages user defined 
			for(String st :packageList){
				skipPackageList.add(st.toUpperCase());
		    }
			
	//Imports 
			log.debug("Imports");
		    IImportDeclaration[] imports = source.getImports();
		    String im;
		    for (IImportDeclaration iImportDeclaration : imports){
		    	im = iImportDeclaration.getElementName();

		    	Boolean in_skip = false;
		    	for (String str : skipPackageList){
			    	if (im.toUpperCase().indexOf(str) != -1)
			    		in_skip = true;
			    }
		    	
		    	if (in_skip == false)
		    		log.debug(im);
		    		importsList.add(im);
		    
		    }
		    log.debug("=====================");
//ITYPEs...	
		    IType [] types = source.getAllTypes();
		    for (IType itype : types){
		    	IJavaElement[] ee = itype.getChildren();
		    	log.debug("****************");
		    	for(IJavaElement e:ee){
		    		log.debug(e.getElementName());
		    	}
		    	log.debug("****************");
	//Annotation
		    	log.debug("Annotation");
		    	for (IAnnotation annotation : itype.getAnnotations()){
		    		for (IMemberValuePair pair : annotation.getMemberValuePairs()){
		    			log.debug(pair.getMemberName());
		    			log.debug("\t"+pair.getValue());
		    		}
		    	}
		    	log.debug("Annotation <<<<<<<<<");
		    	
	//Fields
		    	IField[] fields = itype.getFields(); 
		    	log.debug("Generate field");
		    	
		    	//Field: name, modifier, type,
		    	for (IField field : fields){
		    		Field f = new Field();
		    		String st = Flags.toString(field.getFlags());


		    		if(Flags.isTransient(field.getFlags()))
		    			st = st + " transient";
		    		
		    		f.setFieldName(field.getElementName());
		    		f.setFieldPublicity(st);
		    		f.setFieldType(CurlSpecUtil.marshalCurlType(Signature.toString(field.getTypeSignature()),true,true));
		    		if(Flags.isStatic(field.getFlags()))
		    			f.setFieldIsStatic("let");
		    		else
		    			f.setFieldIsStatic("field");
		    		fieldsList.add(f);
		    		
		    	}
		    	for(Field fie:fieldsList){
		    		log.debug(fie.getFieldIsStatic());
		    		log.debug("\t"+fie.getFieldPublicity());
		    		log.debug("\t"+fie.getFieldName());
		    		log.debug("\t"+fie.getFieldType()+"\n");
		    	}
		    	log.debug(fieldsList.size());
		    	

		    	log.debug("****************");
	//Methods
		    	log.debug("Methods");
		    	IMethod[] methods = itype.getMethods();
		    	for (IMethod method : methods){
		    		
		    		String sn = method.getElementName();
		    		methodNameList.add(sn);
		    		methodNameList4Curl.add(CurlSpecUtil.marshalCurlName(sn, true));
		    		log.debug(sn);
		    		for (IAnnotation annotation : itype.getAnnotations()){
		    			log.debug("Method Annotation"+annotation.getElementName());
			    		for (IMemberValuePair pair : annotation.getMemberValuePairs()){
			    			log.debug("\t"+pair.getMemberName());
			    			log.debug("\t"+pair.getValue());
			    		}
			    	}
		    		String[] paramNames = method.getParameterNames();
		    		String[] paramTypes = method.getParameterTypes();
		    		
		    		String noParam = "NoParams";
		    		String noReturn = "Empty";
		    		String paramName = null;
		    		for (int i = 0; i<paramNames.length; i++){
		    			paramName = CurlSpecUtil.marshalCurlName(paramNames[i], true);
		    			if (i==0){
		    				noParam = paramName + ":" + CurlSpecUtil.marshalCurlType(Signature.toString(paramTypes[i]),true,true);
		    				noReturn = paramName;
		    			} else {
		    				noParam = noParam + ", " + paramName + ":" + CurlSpecUtil.marshalCurlType(Signature.toString(paramTypes[i]),true,true);
		    				noReturn = noReturn + ", " + paramName;
		    			}
		    				
		    			log.debug("\t" +paramNames[i]);
		    			log.debug("\t"+CurlSpecUtil.marshalCurlType(Signature.toString(paramTypes[i]),true,true));
		    		}
		    		
		    		methodParamList.add(noParam);
		    		methodArguments4Curl.add(noReturn);
		    		log.debug("\t"+Signature.toString(method.getReturnType()));
		    		methodReturnTypeList.add(CurlSpecUtil.marshalCurlType(Signature.toString(method.getReturnType()),true,true));
		    	}
		    	log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<");
		    	for (int i=0; i<methodNameList.size(); i++){
		    		log.debug(methodNameList.get(i)+" "+
		    				methodParamList.get(i)+"   "+
		    				methodReturnTypeList.get(i)+"   "+
		    				methodArguments4Curl.get(i)
		    				);
		    	}
		    	log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>");
		    }
		    log.debug("=====================");
		    
		    log.debug("WWWWWWWWWWWWWWWWWWWWWWWWWWW");
			
*/			
		   generateDate = CurlClassComponentsGen.generateDateTime();
		   importsList = CurlClassComponentsGen.generateImportsList(source);
		   fieldsList = CurlClassComponentsGen.generateFieldsList(source);
		   packageName = CurlClassComponentsGen.generatePackageName(source);
		   packageName4Curl = packageName.toUpperCase();
		   methodsList = CurlClassComponentsGen.generateMethodsList(source);
		   className = CurlClassComponentsGen.generateClassName(source);
		   
		   fileUrl = className + fileUrl;
 		
			VelocityContext context = new VelocityContext();
			VelocityContext contextPackageFile = new VelocityContext();
			
	//put necessary info into context for generating the Curl source code		
			context.put("generateDate", generateDate);
			context.put("importsList", importsList);
			context.put("fieldsList", fieldsList);
			context.put("packageName", packageName);
			context.put("packageName4Curl", packageName4Curl);
			context.put("className",className );
			context.put("methodNameList", methodNameList);
			context.put("methodNameList4Curl", methodNameList4Curl);
			context.put("methodParamList", methodParamList);
			context.put("methodReturnTypeList", methodReturnTypeList );
			context.put("methodArguments4Curl", methodArguments4Curl );
			context.put("methodsList", methodsList);
	
	//put necessary info into context for generating the load file		
			contextPackageFile.put("rteVersion", rteVersion );
			contextPackageFile.put("packageName4Curl", packageName4Curl );
			contextPackageFile.put("fileUrl", fileUrl);
			

	//write Curl source code file
			

			File file = new File(fileUrl);
            FileWriter fileWriter = new FileWriter(file);
            

            Template template = Velocity.getTemplate("templates/HttpSession.vm");
            template.merge(context, fileWriter);
            fileWriter.flush();

            

      //create or rewrite load file     
            File load = new File(loadFileUrl);
	        if(!load.exists()){
	           FileWriter fileWriter2 = new FileWriter(load);
	          	
 	           Template template2 = Velocity.getTemplate("templates/load.vm");
 	           template2.merge(contextPackageFile, fileWriter2);
 	           fileWriter2.flush();
 	            
	         }else{
	        	log.debug("Load file Already exits!");
	            FileReader in = new FileReader(load);
	            BufferedReader br = new BufferedReader(in);
	            String line;
	            Boolean included = false;
	            log.debug(fileUrl);
	            while ((line = br.readLine()) != null) {
	            	log.debug(line);
	                if(line.indexOf("\"" + fileUrl + "\"") != -1)
	                	included = true;
	            }
	            br.close();
	            in.close();
	            
	           	if(!included){
	           		FileWriter fileWriter2 = new FileWriter(load, true);
	           		fileWriter2.write("\n{include \""+fileUrl+"\"}");
		            fileWriter2.flush();
	           	}
	         }

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.debug("");
		log.debug("==================End=================");
		log.debug("");
		System.out.println("f");
		System.out.println(fieldsList.size());
		for(Method m : methodsList){
			System.out.println(m.getMethodParams());
		}
	}
	
	

}
