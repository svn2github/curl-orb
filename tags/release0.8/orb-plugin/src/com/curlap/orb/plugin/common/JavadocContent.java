package com.curlap.orb.plugin.common;

import java.util.ArrayList;
import java.util.List;

public class JavadocContent {

	private String purpose;
	private String author;
	private List<JavadocParamContent> params;
	private String returnValue;
	
	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public List<JavadocParamContent> getParams() {
		if (params == null)
			params = new ArrayList<JavadocParamContent>(); 
		return params;
	}
	
	public void setParams(List<JavadocParamContent> params) {
		this.params = params;
	}
	
	public String getReturnValue() {
		return returnValue;
	}
	
	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}

}
