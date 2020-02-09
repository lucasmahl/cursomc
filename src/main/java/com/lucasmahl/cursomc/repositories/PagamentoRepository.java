package com.lucasmahl.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucasmahl.cursomc.domain.Pagamento;

//Jpa Repository tipo especial do spring capaz de acessar os dados do tipo q for informado
@Repository	//não é necessário repository das subclasses PagamentoComBoleto nem PagamentoComCartao
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer>{//Integer é o tipo do Id
	
}
