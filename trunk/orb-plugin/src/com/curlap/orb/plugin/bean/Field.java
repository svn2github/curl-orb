package com.curlap.orb.plugin.bean;

public class Field {
	public String fieldIsStatic = "field";//let, field
	public String fieldPublicity = "public";//public,package,protected,private+(transient)
	public String fieldName= "field_name";//(_)+field name
	public String fieldType = "#String";// ?uninitialized
	public String getFieldIsStatic() {
		return fieldIsStatic;
	}
	public void setFieldIsStatic(String fieldIsStatic) {
		this.fieldIsStatic = fieldIsStatic;
	}
	public String getFieldPublicity() {
		return fieldPublicity;
	}
	public void setFieldPublicity(String fieldPublicity) {
		this.fieldPublicity = fieldPublicity;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	
	
}
