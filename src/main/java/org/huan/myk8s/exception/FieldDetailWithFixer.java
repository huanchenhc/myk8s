package org.huan.myk8s.exception;

public class FieldDetailWithFixer {
	private String fieldName;
	private String message;
	private String fixer; //TODO
	
	public FieldDetailWithFixer(String fieldName, String message) {
		super();
		this.fieldName = fieldName;
		this.message = message;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getFixer() {
		return fixer;
	}
	public void setFixer(String fixer) {
		this.fixer = fixer;
	}
	
}
