package com.lucasmahl.cursomc.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.lucasmahl.cursomc.services.exceptions.ObjectNotFoundException;

//handler q faz o tratamento de exceções
@ControllerAdvice
public class ResourceExceptionHandler {
	//ResponseEntity tipo do spring, q encapsula varias informações de um http p/ um serviço rest
	@ExceptionHandler(ObjectNotFoundException.class)//pra indicar q é um tratador de exceção do tipo ObjectNotFoundException
	public ResponseEntity<StandartError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){//(exceção, info da requisição)
		StandartError err = new StandartError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis()); //objeto não encontrado
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
}
