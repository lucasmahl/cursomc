package com.lucasmahl.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.lucasmahl.cursomc.domain.Cliente;
import com.lucasmahl.cursomc.domain.enums.TipoCliente;
import com.lucasmahl.cursomc.dto.ClienteNewDTO;
import com.lucasmahl.cursomc.repositories.ClienteRepository;
import com.lucasmahl.cursomc.resources.exception.FieldMessage;
import com.lucasmahl.cursomc.services.validation.utils.BR;
//CLASSE Q É O VALIDATOR PRA CLASSE DE ANOTAÇÃO ClienteInsert
//ConstraintValidator<"nome da anotação", "tipo da classe q vai aceitar a anotação">
public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {//"isValid" METODO Q VALIDA, retorna true se for válido
		
		List<FieldMessage> list = new ArrayList<>();//instanciada lista vazia FieldMessage q carrega nome do campo e mensagem de erro do campo
		
		if ( objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj()) ) {
			list.add(new FieldMessage("CpfOuCnpj", "CPF inválido."));
		}
		
		if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj()) ) {
			list.add(new FieldMessage("CpfOuCnpj", "CNPJ inválido."));
		}

		//testa se email do cliente já existe
		Cliente aux = repo.findByEmail(objDto.getEmail());
		if (aux != null) {
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
