package com.lucasmahl.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lucasmahl.cursomc.domain.Produto;
import com.lucasmahl.cursomc.dto.ProdutoDTO;
import com.lucasmahl.cursomc.resources.utils.URL;
import com.lucasmahl.cursomc.services.ProdutoService;

//Resource pq é um controlador Rest
@RestController // diretiva do spring boot
@RequestMapping(value = "/produtos") // endpont
public class ProdutoResource {

	@Autowired //pra instanciar automaticamente pelo spring
	private ProdutoService service;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET) // verbo http
	public ResponseEntity<Produto> find(@PathVariable Integer id) {//PathVariablepq o id da url vai vir pra o da variavel
		//ResponseEntity tipo do spring, q encapsula varias informações de um http p/ um serviço rest
		//ResponseEntity<?> pq pode ser qualquer tipo, até nulo
		
		Produto obj = service.find(id);
		
		return ResponseEntity.ok().body(obj);

	}
	
	@RequestMapping(method = RequestMethod.GET) // GET, verbo http //value = http://localhost:8080/produto, não será /page, como em categoria
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value = "nome", defaultValue = "")String nome, //parametro de URL é string
			@RequestParam(value = "categorias", defaultValue = "")String categorias,
			@RequestParam(value = "page", defaultValue = "0")Integer page,//RequestParam pq é um parametro opcional
			@RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome")String orderBy,//ordenado por nome
			@RequestParam(value = "direction", defaultValue = "ASC")String direction) {
		//ResponseEntity tipo do spring, q encapsula varias informações de um http p/ um serviço rest
		//ResponseEntity<?> pq pode ser qualquer tipo, até nulo
		
		String nomeDecoded = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeIntList(categorias);
		
		Page<Produto> list = service.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction); //função do service
		Page<ProdutoDTO> listDTO = list.map(obj->new ProdutoDTO(obj)); //map faz uma operação pra cada elemento da lista
		
		return ResponseEntity.ok().body(listDTO);
	}
}
