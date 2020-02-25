package com.lucasmahl.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucasmahl.cursomc.domain.Cidade;
import com.lucasmahl.cursomc.domain.Cliente;
import com.lucasmahl.cursomc.domain.Endereco;
import com.lucasmahl.cursomc.domain.enums.TipoCliente;
import com.lucasmahl.cursomc.dto.ClienteDTO;
import com.lucasmahl.cursomc.dto.ClienteNewDTO;
import com.lucasmahl.cursomc.repositories.ClienteRepository;
import com.lucasmahl.cursomc.repositories.EnderecoRepository;
import com.lucasmahl.cursomc.services.exceptions.DataIntegrityException;
import com.lucasmahl.cursomc.services.exceptions.ObjectNotFoundException;

//REGRAS DE NEGOCIO
@Service
public class ClienteService {
	@Autowired // vai ser automaticamente instanciada pelo spring
	private ClienteRepository repo;

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	// busca Cliente pelo Id, e retorna exceção caso ele não exista
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	@Transactional
	public Cliente insert(Cliente obj) {
		//por garantia, id novo tem q ser null
		obj.setId(null);
		
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());//funciona pq tem "cli.getEnderecos().add(end);" no fromDTO() abaixo
		
		return obj;
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());//confere se id realmente existe, pra atualizar
		
		updateData(newObj, obj);
		
		//metodo save igual ao de inserir, porém como tem id != nulo, diferente do insert acima, então atualiza
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
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null, null);	
	}
	
	//sobrecarga do metodo acima
	public Cliente fromDTO(ClienteNewDTO objDTO) {
		Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(), 
				TipoCliente.toEnum(objDTO.getTipo()), pe.encode(objDTO.getSenha()) );
		
		Cidade cid = new Cidade(objDTO.getCidadeId(), null, null);
		
		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), 
				objDTO.getComplemento(), objDTO.getBairro(), objDTO.getCep(), cli, cid); //o cód da cidade vai vir no DTO
	
		cli.getEnderecos().add(end);
		
		cli.getTelefones().add(objDTO.getTelefone1());
		//se tiver mais de um telefone
		if (objDTO.getTelefone2() != null) {
			cli.getTelefones().add(objDTO.getTelefone2());
		}
		if (objDTO.getTelefone3() != null) {
			cli.getTelefones().add(objDTO.getTelefone3());
		}
		
		return cli;
	}
		
	//vai ser private pq é u metódo auxiliar desta mesma classe
	private void updateData(Cliente newObj, Cliente obj) {//não irá atualizar pra null o Tipo e nem o CpfOuCnpj do Cliente
		//irá trocar somente nome e e-mail
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
