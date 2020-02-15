package com.lucasmahl.cursomc.services.exceptions;

public class DataIntegrityException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DataIntegrityException(String msg) {
		super(msg);
	}
	
	public DataIntegrityException(String msg, Throwable cause) {//(mensagem, outra exceção da causa q aconteceu antes)
		super(msg, cause);
	}
}
