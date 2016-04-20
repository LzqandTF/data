package cn.com.chinaebi.dz.util;

public class CustomDzFileInfoClass {
	//表object_relevance_column中字段
	private int object_id;
	private int dz_column_id;
	private int rule_id;
	private int file_type;
	private String show_attribute_name;
	//表custom_object中字段
	private String object_name;
	//表dz_file_column_conf中字段
	private String attribute_name;
	private String attribute_column;
	private String attribute_type;
	//表rule_handler中字段
	private String old_value;
	private String new_value;
	private int handler_type;
	private int template_id;
	//表rule_template中字段
	private String template_name;
	private String template_function;

	public CustomDzFileInfoClass() {
		super();
	}
	public CustomDzFileInfoClass(int file_type,String show_attribute_name,
			String attribute_name,String attribute_column,String old_value,String new_value,int handler_type,int template_id){
		this.file_type = file_type;
		this.attribute_column = attribute_column;
		this.attribute_name = attribute_name;
		this.show_attribute_name = show_attribute_name;
		this.old_value = old_value;
		this.new_value = new_value;
		this.handler_type = handler_type;
		this.template_id = template_id;
	}
	public int getObject_id() {
		return object_id;
	}
	public void setObject_id(int object_id) {
		this.object_id = object_id;
	}
	public int getDz_column_id() {
		return dz_column_id;
	}
	public void setDz_column_id(int dz_column_id) {
		this.dz_column_id = dz_column_id;
	}
	public int getRule_id() {
		return rule_id;
	}
	public void setRule_id(int rule_id) {
		this.rule_id = rule_id;
	}
	public int getFile_type() {
		return file_type;
	}
	public void setFile_type(int file_type) {
		this.file_type = file_type;
	}
	public String getShow_attribute_name() {
		return show_attribute_name;
	}
	public void setShow_attribute_name(String show_attribute_name) {
		this.show_attribute_name = show_attribute_name;
	}
	public String getObject_name() {
		return object_name;
	}
	public void setObject_name(String object_name) {
		this.object_name = object_name;
	}
	public String getAttribute_name() {
		return attribute_name;
	}
	public void setAttribute_name(String attribute_name) {
		this.attribute_name = attribute_name;
	}
	public String getAttribute_column() {
		return attribute_column;
	}
	public void setAttribute_column(String attribute_column) {
		this.attribute_column = attribute_column;
	}
	public String getAttribute_type() {
		return attribute_type;
	}
	public void setAttribute_type(String attribute_type) {
		this.attribute_type = attribute_type;
	}
	public String getOld_value() {
		return old_value;
	}
	public void setOld_value(String old_value) {
		this.old_value = old_value;
	}
	public String getNew_value() {
		return new_value;
	}
	public void setNew_value(String new_value) {
		this.new_value = new_value;
	}
	public int getHandler_type() {
		return handler_type;
	}
	public void setHandler_type(int handler_type) {
		this.handler_type = handler_type;
	}
	public int getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(int template_id) {
		this.template_id = template_id;
	}
	public String getTemplate_name() {
		return template_name;
	}
	public void setTemplate_name(String template_name) {
		this.template_name = template_name;
	}
	public String getTemplate_function() {
		return template_function;
	}
	public void setTemplate_function(String template_function) {
		this.template_function = template_function;
	}
	
	
}
