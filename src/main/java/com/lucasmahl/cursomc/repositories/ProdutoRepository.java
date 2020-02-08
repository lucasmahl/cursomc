package com.lucasmahl.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucasmahl.cursomc.domain.Produto;

//JpaRepository tipo especial do spring capaz de acessar os dados do tipo q for informado
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer>{//Integer é o tipo do Id
	
}
