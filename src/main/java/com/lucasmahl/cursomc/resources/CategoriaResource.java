package com.lucasmahl.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lucasmahl.cursomc.domain.Categoria;
import com.lucasmahl.cursomc.services.CategoriaService;

//Resource pq é um controlador Rest
@RestController // diretiva do spring boot
@RequestMapping(value = "/categorias") // endpont
public class CategoriaResource {

	@Autowired //pra instanciar automaticamente pelo spring
	private CategoriaService service;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET) // verbo http
	public ResponseEntity<?> find(@PathVariable Integer id) {//PathVariablepq o id da url vai vir pra o da variavel
		//ResponseEntity tipo do spring, q encapsula varias informações de um http p/ um serviço rest
		//? pq pode ser qualquer tipo, até nulo
		
		Categoria obj = service.buscar(id);
		
		return ResponseEntity.ok().body(obj);
	}
}
