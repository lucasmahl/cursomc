package com.lucasmahl.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasmahl.cursomc.domain.Cliente;
import com.lucasmahl.cursomc.repositories.ClienteRepository;
import com.lucasmahl.cursomc.services.exceptions.ObjectNotFoundException;

//REGRAS DE NEGOCIO
@Service
public class ClienteService {
	@Autowired // vai ser automaticamente instanciada pelo spring
	private ClienteRepository repo;

	// busca Cliente pelo Id, e retorna exceção caso ele não exista
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

}
