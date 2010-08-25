package com.curlap.orb.plugin.common;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.TextElement;

public class ASTJavadocVisitor extends ASTVisitor {

	private JavadocContent javadocContent;
	
	public JavadocContent getJavadocContent() {
		return javadocContent;
	}
	
	public boolean visit(Javadoc node) {
		if (javadocContent != null)
			return super.visit(node);
		
		JavadocContent content = new JavadocContent();
		for (Object tag : node.tags()) {
			TagElement element = (TagElement) tag;
			String tagName = element.getTagName();

			// parse tag element depending on its name
			if (tagName == null)
				content.setPurpose(parseToText(element));
			else if (tagName.equals("@param"))
				content.getParams().add(parseToParam(element));
			else if (tagName.equals("@return"))
				content.setReturnValue(parseToText(element));
			else if (tagName.equals("@author"))
				content.setAuthor(parseToText(element));
		}
		javadocContent = content;
		return super.visit(node);
	}
	
	private String parseToText(TagElement element) {
		StringBuilder strBuilder = new StringBuilder();
		for (Object fragment : element.fragments())
			if (fragment instanceof TextElement) {
				String text = ((TextElement) fragment).getText();
				strBuilder.append(text);
				strBuilder.append('\n');
			}
		return trimSpaces(strBuilder.toString());
	}
	
	private JavadocParamContent parseToParam(TagElement element) {
		JavadocParamContent param = new JavadocParamContent();
		for (Object fragment : element.fragments())					
			if (fragment instanceof SimpleName) {
				String name = ((SimpleName) fragment).getIdentifier();
				param.setName(trimSpaces(name));
			} else if (fragment instanceof TextElement) {
				String text = ((TextElement) fragment).getText();
				param.setText(trimSpaces(text));
			}
		return param;
	}
	
	private String trimSpaces(String str) {
		final int count = str.length();
	    int len = count;
	    int st = 0;
	    char[] val = str.toCharArray();

	    while (st < len && (val[st] <= ' ' || val[st] == '\u3000')) {
	        st++;
	    }
	    while (st < len && (val[len - 1] <= ' ' || val[st] == '\u3000')) {
	        len--;
	    }
	    return (st > 0 || len < count) ? str.substring(st, len) : str;
	}	
}
