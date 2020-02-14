package com.lucasmahl.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lucasmahl.cursomc.domain.Pedido;
import com.lucasmahl.cursomc.services.PedidoService;

//Resource pq é um controlador Rest
@RestController // diretiva do spring boot
@RequestMapping(value = "/pedidos") // endpont
public class PedidoResource {

	@Autowired //pra instanciar automaticamente pelo spring
	private PedidoService service;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET) // verbo http
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {//PathVariablepq o id da url vai vir pra o da variavel
		//ResponseEntity tipo do spring, q encapsula varias informações de um http p/ um serviço rest
		//ResponseEntity<?> pq pode ser qualquer tipo, até nulo
		
		Pedido obj = service.find(id);
		
		return ResponseEntity.ok().body(obj);

	}
}
