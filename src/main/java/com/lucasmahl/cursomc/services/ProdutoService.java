package com.lucasmahl.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.lucasmahl.cursomc.domain.Categoria;
import com.lucasmahl.cursomc.domain.Produto;
import com.lucasmahl.cursomc.repositories.CategoriaRepository;
import com.lucasmahl.cursomc.repositories.ProdutoRepository;
import com.lucasmahl.cursomc.services.exceptions.ObjectNotFoundException;

//REGRAS DE NEGOCIO
@Service
public class ProdutoService {
	@Autowired // vai ser automaticamente instanciada pelo spring
	private ProdutoRepository repo;

	@Autowired
	private CategoriaRepository categoriaRepository; //vai ser usado pra gerar a lista de categorias
	
	// busca Produto pelo Id, e retorna exceção caso ele não exista
	public Produto find(Integer id) {
		Optional<Produto> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}

	// busca paginada, por categoria
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy,
			String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		List<Categoria> categorias =  categoriaRepository.findAllById(ids);
		
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);

	}
}
