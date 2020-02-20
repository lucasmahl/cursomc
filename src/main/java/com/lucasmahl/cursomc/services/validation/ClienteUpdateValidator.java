package com.lucasmahl.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.lucasmahl.cursomc.domain.Cliente;
import com.lucasmahl.cursomc.dto.ClienteDTO;
import com.lucasmahl.cursomc.repositories.ClienteRepository;
import com.lucasmahl.cursomc.resources.exception.FieldMessage;
//CLASSE Q É O VALIDATOR PRA CLASSE DE ANOTAÇÃO ClienteUpdate
//ConstraintValidator<"nome da anotação", "tipo da classe q vai aceitar a anotação">
public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
	
	//pra pegar o id da uri, pq o id não estará no corpo do json
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {//"isValid" METODO Q VALIDA, retorna true se for válido
		
		//Map é uma estrutura de dados, chave/valor, diferente map(), auxilia ao pegar chave e valor da uri
		@SuppressWarnings("unchecked")//@SuppressWarnings usado somente para tirar o alerta amarelo q estava na linha abaixo (ñ é necessário)
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId =  Integer.parseInt(map.get("id"));
		
		List<FieldMessage> list = new ArrayList<>();//instanciada lista vazia FieldMessage q carrega nome do campo e mensagem de erro do campo

		//testa se email do cliente já existe
		Cliente aux = repo.findByEmail(objDto.getEmail());
		if (aux != null && !aux.getId().equals(uriId)) {//&& verifica se outro id possui o mesmo email, tmbm
			list.add(new FieldMessage("email", "E-mail já existe no banco."));
		}
		
		//for do framework q transofrma a lista de fieldmessage em objeto pra leitura
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		//se lista for vazia retorna true, q é válido e não possui erro
		return list.isEmpty();
	}
}
