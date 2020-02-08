package com.lucasmahl.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasmahl.cursomc.domain.Categoria;
import com.lucasmahl.cursomc.repositories.CategoriaRepository;
import com.lucasmahl.cursomc.services.exceptions.ObjectNotFoundException;

//REGRAS DE NEGOCIO
@Service
public class CategoriaService {
	@Autowired // vai ser automaticamente instanciada pelo spring
	private CategoriaRepository repo;

	//busca categoria pelo Id, e retorna exceção caso ele não exista
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

}
