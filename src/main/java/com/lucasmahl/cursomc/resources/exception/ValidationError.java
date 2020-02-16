package com.lucasmahl.cursomc.resources.exception;

import java.util.ArrayList;
import java.util.List;

//trabalha junto com a classe FieldMessage
public class ValidationError extends StandartError{
	private static final long serialVersionUID = 1L;

	private List<FieldMessage> errors = new ArrayList<>();
	
	public ValidationError(Integer status, String msg, Long timeStamp) {
		super(status, msg, timeStamp);
		// TODO Auto-generated constructor stub
	}

	public List<FieldMessage> getErrors() {
		return errors;
	}

	public void addError(String fieldName, String message) {
		errors.add(new FieldMessage(fieldName, message));
	}

	
}
