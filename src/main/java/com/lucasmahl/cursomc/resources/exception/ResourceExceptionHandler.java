package com.lucasmahl.cursomc.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.lucasmahl.cursomc.services.exceptions.DataIntegrityException;
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
	
	//qndo for excluir objeto q possui filhos associados
	//ResponseEntity tipo do spring, q encapsula varias informações de um http p/ um serviço rest
	@ExceptionHandler(DataIntegrityException.class)//pra indicar q é um tratador de exceção do tipo DataIntegrityException
	//Em "dataIntegrity", pode ser qualeur nome
	public ResponseEntity<StandartError> dataIntegrity(DataIntegrityException e, HttpServletRequest request){//(exceção, info da requisição)
		StandartError err = new StandartError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis()); //objeto possui dependentes
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	//pra resumir o erro gerado, qndo for inserir um dado q não está dentro do padrão
	//ResponseEntity tipo do spring, q encapsula varias informações de um http p/ um serviço rest
	@ExceptionHandler(MethodArgumentNotValidException.class)//pra indicar q é um tratador de exceção do tipo DataIntegrityException
	//Em "dataIntegrity", pode ser qualeur nome
	public ResponseEntity<StandartError> validation(MethodArgumentNotValidException e, HttpServletRequest request){//(exceção, info da requisição)
		ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de validação", System.currentTimeMillis()); //objeto possui dependentes
		
		//percorre a lista de erros q tem em MethodArgumentNotValidException e, e pra cada erro gera um FieldMessage
		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
}
