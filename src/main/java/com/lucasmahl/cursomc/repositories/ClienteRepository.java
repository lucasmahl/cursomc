package com.lucasmahl.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lucasmahl.cursomc.domain.Cliente;

//JpaRepository tipo especial do spring capaz de acessar os dados do tipo q for informado
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {// Integer é o tipo do Id

	@Transactional(readOnly=true)// readOnly, não necessita ser envolvida com uma operação de banco
	Cliente findByEmail(String email); //pra conferir se e-mail q irá ser inserido, já existe na base
	
	
	
}
