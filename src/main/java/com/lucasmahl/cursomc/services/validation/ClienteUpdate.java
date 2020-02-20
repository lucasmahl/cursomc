package com.lucasmahl.cursomc.services.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

//ANOTAÇÃO PERSONALIZADA

@Constraint(validatedBy = ClienteUpdateValidator.class) //"ClienteUpdateValidator" será a classe que implementa o validator
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ClienteUpdate {//"ClienteUpdate" é a anotação
	String message() default "Erro de validação";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
