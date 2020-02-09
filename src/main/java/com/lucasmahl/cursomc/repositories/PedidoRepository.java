package com.lucasmahl.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucasmahl.cursomc.domain.Pedido;

//Jpa Repository tipo especial do spring capaz de acessar os dados do tipo q for informado
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer>{//Integer é o tipo do Id
	
}
