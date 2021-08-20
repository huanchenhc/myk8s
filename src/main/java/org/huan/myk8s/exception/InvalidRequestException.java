package org.huan.myk8s.exception;

import java.util.ArrayList;
import java.util.List;

public class InvalidRequestException extends CommonException {
	
	private List<FieldDetailWithFixer> fields = new ArrayList<>();
	public InvalidRequestException(String errCode, String message,  List<FieldDetailWithFixer> fields) {
		super(errCode, message);
		this.fields.addAll(fields);
	}

	public void addField(FieldDetailWithFixer f) {
		fields.add(f);
	}

	public void addAllFields(List<FieldDetailWithFixer> fs) {
		fields.addAll(fs);
	}
	
	public List<FieldDetailWithFixer> getFields(){
		return fields;
		
	}
}

