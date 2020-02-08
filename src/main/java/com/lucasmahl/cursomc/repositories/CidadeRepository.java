package com.lucasmahl.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucasmahl.cursomc.domain.Cidade;

//JpaRepository tipo especial do spring capaz de acessar os dados do tipo q for informado
@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer>{//Integer é o tipo do Id
	
}
