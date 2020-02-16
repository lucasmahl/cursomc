package com.lucasmahl.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.lucasmahl.cursomc.domain.Categoria;
import com.lucasmahl.cursomc.dto.CategoriaDTO;
import com.lucasmahl.cursomc.repositories.CategoriaRepository;
import com.lucasmahl.cursomc.services.exceptions.DataIntegrityException;
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
	
	public Categoria insert(Categoria obj) {
		//por garantia, id novo tem q ser null
		obj.setId(null);
		
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		Categoria newObj = find(obj.getId());//confere se id realmente existe, pra atualizar
		
		updateData(newObj, obj);
		
		//metodo save igual ao de inserir, porém como tem id != nulo, diferente do insert acima, então apenas atualiza
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id); //caso o id não exista, dispara a exceção feita acima, no find (boas praticas)
		
		
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			//lança exceção personalizada, ao deletar Categoria, caso ela tenha dependencias associadas, como Produtos
			throw new DataIntegrityException("Não é possível excluir Categoria "+id+", pois possui produtos associados");
		}
		
	}
	
	public List<Categoria> findAll(){
		return repo.findAll();
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		return repo.findAll(pageRequest);
		
	}
	
	//metodo auxiliar pra conversao de dto
	public Categoria fromDTO(CategoriaDTO objDTO) {
		return new Categoria(objDTO.getId(), objDTO.getNome());	
	}
	
	//vai ser private pq é u metódo auxiliar desta mesma classe
		private void updateData(Categoria newObj, Categoria obj) {
			newObj.setNome(obj.getNome());
		}

}
