package com.lucasmahl.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucasmahl.cursomc.domain.Estado;

//JpaRepository tipo especial do spring capaz de acessar os dados do tipo q for informado
@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer>{//Integer é o tipo do Id
	
}
