package com.lucasmahl.cursomc.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	
	//metodo pra receber pedido em json e inseri-la no banco
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody Pedido obj){//@RequestBody pra construir a pedido através do json //@Valid pra q o obj seja validado antes
		obj = service.insert(obj);//tem "obj =" pq save do repository retorna obj
		
		//uri de resposta, retorna a uri q foi salva, tipo: http://localhost:8080/categorias/3
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")//fromCurrentRequest pega uri q foi usada pra inserir
				.buildAndExpand(obj.getId()).toUri();//buildAndExpand pra atribuir valor
		
		return ResponseEntity.created(uri).build();//created gera o cód 201, de criação q foi feita com sucesso		
	}
	
	@RequestMapping(method = RequestMethod.GET) // GET, verbo http //value = http://localhost:8080/pedidos/page
	public ResponseEntity<Page<Pedido>> findPage(
			@RequestParam(value = "page", defaultValue = "0")Integer page,//RequestParam pq é um parametro opcional
			@RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "instante")String orderBy,//ordenado por instante
			@RequestParam(value = "direction", defaultValue = "DESC")String direction) {
		//ResponseEntity tipo do spring, q encapsula varias informações de um http p/ um serviço rest
		//ResponseEntity<?> pq pode ser qualquer tipo, até nulo
		
		Page<Pedido> list = service.findPage(page, linesPerPage, orderBy, direction); //função do service
		
		return ResponseEntity.ok().body(list);
	}
}
