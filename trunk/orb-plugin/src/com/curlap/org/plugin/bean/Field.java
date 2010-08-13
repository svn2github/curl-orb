package com.curlap.org.plugin.bean;

public class Field {
	public String field_is_static = "field";//let, field
	public String field_publicity = "public";//public,package,protected,private+(transient)
	public String field_name= "field_name";//(_)+field name
	public String field_type = "#String";// ?uninitialized
	public String getField_is_static() {
		return field_is_static;
	}
	public void setField_is_static(String field_is_static) {
		this.field_is_static = field_is_static;
	}
	public String getField_publicity() {
		return field_publicity;
	}
	public void setField_publicity(String field_publicity) {
		this.field_publicity = field_publicity;
	}
	public String getField_name() {
		return field_name;
	}
	public void setField_name(String field_name) {
		this.field_name = field_name;
	}
	public String getField_type() {
		return field_type;
	}
	public void setField_type(String field_type) {
		this.field_type = field_type;
	}
	
}
