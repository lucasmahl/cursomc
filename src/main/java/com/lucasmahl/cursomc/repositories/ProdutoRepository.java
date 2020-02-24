package com.lucasmahl.cursomc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lucasmahl.cursomc.domain.Categoria;
import com.lucasmahl.cursomc.domain.Produto;

//JpaRepository tipo especial do spring capaz de acessar os dados do tipo q for informado
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {// Integer é o tipo do Id

	
	//busca de produto por categoria//@Query do sprng data, pode por jpql dentro, q o framework pre-processa
	//@Query("SELECT DISTINCT obj from Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat in :categorias")
	@Transactional(readOnly=true)//pq é só consulta, não é necessário iniciar uma tranzação
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(/*@Param("nome") */String nome,/*@Param("categorias")*/ List<Categoria> categorias, Pageable pageRequest);//@param pra jogar na query acima
	//findDistinctByNome metodo do spring-data, Containing pq é 'like = %:nome%' 
}
