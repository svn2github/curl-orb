package com.curlap.orb.plugin.common;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.TagElement;

public class ASTJavadocVisitor extends ASTVisitor {

	private JavadocContent javaDoc;
	
	public JavadocContent getJavadoc() {
		return javaDoc;
	}
	
	public boolean visit(Javadoc node) {
		JavadocContent content = new JavadocContent();
		for (Object tagObj : node.tags()) {
//			TagElement tag = (TagElement) tagObj;
//			String tagName = tag.getTagName();
//			String tagContent = tag.toString();
//			System.out.println(tagName + ":" + tagContent);
//			if (tagName == null)
//				content.purpose = tagContent;
//			else if (tagName.equals("@param"))
//				content.params = tagContent;
//			else if (tagName.equals("@return"))
//				content.returnValue = tagContent;
//			
//			for (Object fragment : tag.fragments()) {
//				System.out.println(fragment.getClass().getName() + ":" + fragment);
//			}
//			System.out.println("=============================");
		}
		javaDoc = content;
		return super.visit(node);
	}
	
	
	public class JavadocContent {

		private String purpose;
		private String author;
		private Map<String, String> params;
		private String returnValue;
		
		public JavadocContent() {
			purpose = "";
			author = "";
			params = new LinkedHashMap<String, String>();
			returnValue = "";
		}
		
		public String getPurpose() {
			return purpose;
		}

		public String getAuthor() {
			return author;
		}

		public Map<String, String> getParams() {
			return params;
		}

		public String getReturnValue() {
			return returnValue;
		}
	}
}
