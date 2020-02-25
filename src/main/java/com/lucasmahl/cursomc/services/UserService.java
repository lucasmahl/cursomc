package com.lucasmahl.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.lucasmahl.cursomc.security.UserSS;

public class UserService {

	public static UserSS authenticated() {
		try {
			//retorna usuário logado
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}catch (Exception e) {
			//senão achar, retorna nulo
			return null;
		}
	}
}
