package com.lucasmahl.cursomc.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//Resource pq é um controlador Rest
@RestController //diretiva do spring boot
@RequestMapping(value="/categorias")//endpont
public class CategoriaResource {
	
	@RequestMapping(method=RequestMethod.GET) //verbo http
	public String listar() {
		return "Rest tá funcionando";
	}
}
