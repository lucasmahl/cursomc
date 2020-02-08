package com.lucasmahl.cursomc.services.exceptions;

public class ObjectNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ObjectNotFoundException(String msg) {
		super(msg);
	}
	
	public ObjectNotFoundException(String msg, Throwable cause) {//(mensagem, outra exceção da causa q aconteceu antes)
		super(msg, cause);
	}
}
