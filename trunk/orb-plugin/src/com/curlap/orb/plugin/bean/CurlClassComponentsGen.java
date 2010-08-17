package com.curlap.orb.plugin.bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IImportDeclaration;
import org.eclipse.jdt.core.IMemberValuePair;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageDeclaration;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;

import com.curlap.orb.plugin.common.CurlSpecUtil;

public class CurlClassComponentsGen {
	
	public static String generateDateTime(){
		 return SimpleDateFormat.getInstance().format(new Date());
	}
	
	public static String generateClassName(ICompilationUnit source){
		String s = source.getElementName();
		String className = s.substring(0, s.length()-5);
		return className;
	}
	
	public static String generatePackageName(ICompilationUnit source) throws JavaModelException{
		IPackageDeclaration[] packageDeclarations = source.getPackageDeclarations();
		String s = null;
		for (IPackageDeclaration iPackageDeclaration : packageDeclarations){
			s = iPackageDeclaration.getElementName();
		}
		return s;
	}
	public static List<String> generateImportsList(ICompilationUnit source) throws JavaModelException{
		List<String> importsList = new ArrayList<String>();
		List<String> skipPackageList = new ArrayList<String>();
		
		skipPackageList.add("COM.CURL.IO.SERIALIZE.TYPES");
		skipPackageList.add("JAVA.LANG");
		skipPackageList.add("JAVA.UTIL");
		skipPackageList.add("JAVA.SQL");
		skipPackageList.add("JAVA.MATH");
		
		IImportDeclaration[] imports = source.getImports();
		String st;
	    for (IImportDeclaration iImportDeclaration : imports){
	    	st = iImportDeclaration.getElementName();
	  
	    	Boolean in_skip = false;
	    	for (String str : skipPackageList){
		    	if (st.toUpperCase().indexOf(str) != -1)
		    		in_skip = true;
		    }

	    	if (in_skip == false)
	    		importsList.add(st);
	    }
	    return importsList;

	}
	
	public static List<Field> generateFieldsList(ICompilationUnit source) throws JavaModelException{
		List<Field> fieldsList = new ArrayList<Field>();
		
		IType [] types = source.getAllTypes();
		for (IType itype : types){
			//Annotation
	    	System.out.println("Annotation");
	    	for (IAnnotation annotation : itype.getAnnotations()){
	    		for (IMemberValuePair pair : annotation.getMemberValuePairs()){
	    			System.out.println(pair.getMemberName());
	    			System.out.println("\t"+pair.getValue());
	    		}
	    	}
		    
	    	IField[] fields = itype.getFields(); 
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
	    	
		}
		
		return fieldsList;
	}
	
	public static List<Method> generateMethodsList(ICompilationUnit source) throws JavaModelException{
		List<Method> methodsList = new ArrayList<Method>();
		
	    for (IType itype : source.getAllTypes()){
	    	IMethod[] methods = itype.getMethods();
	    	for (IMethod method : methods){
	    		
	    		//Annotation
		    	System.out.println("Annotation");
		    	for (IAnnotation annotation : itype.getAnnotations()){
		    		for (IMemberValuePair pair : annotation.getMemberValuePairs()){
		    			System.out.println(pair.getMemberName());
		    			System.out.println("\t"+pair.getValue());
		    		}
		    	}
		    	
		    	Method m = new Method();
		    	
	    		String sn = method.getElementName();
	    		m.setMethodName(sn);
	    		m.setMethodName4Curl(CurlSpecUtil.marshalCurlName(sn, true));
	    		
	    		String[] paramNames = method.getParameterNames();
	    		String[] paramTypes = method.getParameterTypes();
	    		
	    		String noParams = "NoParams";
	    		String noReturn = "Empty";
	    		String noArgumetns = "Empty";
	    		String paramName = null;
	    		
	    		for (int i = 0; i<paramNames.length; i++){
	    			paramName = CurlSpecUtil.marshalCurlName(paramNames[i], true);
	    			if (i==0){
	    				noParams = paramName + ":" + CurlSpecUtil.marshalCurlType(Signature.toString(paramTypes[i]),true,true);
	    				noArgumetns = paramName;
	    			} else {
	    				noParams = noParams + ", " + paramName + ":" + CurlSpecUtil.marshalCurlType(Signature.toString(paramTypes[i]),true,true);
	    				noArgumetns = noReturn + ", " + paramName;
	    			}
	    		}
	    		
	    		m.setMethodParams(noParams);
	    		m.setMethodArguments4Curl(noArgumetns);
	    		
	    		noReturn = CurlSpecUtil.marshalCurlType(Signature.toString(method.getReturnType()),true,true);
	    		
	    		m.setMethodReturnType(noReturn);

	    		
	    		methodsList.add(m);
	    	}
	    }
		
		
		return methodsList;		
		
	}
	


	
	
	
}
