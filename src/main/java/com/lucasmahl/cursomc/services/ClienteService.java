package com.lucasmahl.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.lucasmahl.cursomc.domain.Cliente;
import com.lucasmahl.cursomc.dto.ClienteDTO;
import com.lucasmahl.cursomc.repositories.ClienteRepository;
import com.lucasmahl.cursomc.services.exceptions.DataIntegrityException;
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

	public Cliente insert(Cliente obj) {
		//por garantia, id novo tem q ser null
		obj.setId(null);
		
		return repo.save(obj);
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());//confere se id realmente existe, pra atualizar
		
		updateData(newObj, obj);
		
		//metodo save igual ao de inserir, porém como tem id != nulo, igual acima, então atualizada
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id); //caso o id não exista, dispara a exceção feita acima, no find (boas praticas)
		
		
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			//lança exceção personalizada, ao deletar Cliente, caso ela tenha dependencias associadas, como Produtos
			throw new DataIntegrityException("Não é possível excluir Cliente "+id+", pois possui entidades relacionadas.");
		}
		
	}
	
	public List<Cliente> findAll(){
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		return repo.findAll(pageRequest);
		
	}
	
	//metodo auxiliar pra conversao de dto
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);	
	}
	
	//vai ser private pq é u metódo auxiliar desta mesma classe
	private void updateData(Cliente newObj, Cliente obj) {//não irá atualizar pra null o Tipo e nem o CpfOuCnpj do Cliente
		//irá trocar somente nome e e-mail
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
