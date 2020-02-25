package com.lucasmahl.cursomc.services.exceptions;

public class AuthorizationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AuthorizationException(String msg) {
		super(msg);
	}
	
	public AuthorizationException(String msg, Throwable cause) {//(mensagem, outra exceção da causa q aconteceu antes)
		super(msg, cause);
	}
}
