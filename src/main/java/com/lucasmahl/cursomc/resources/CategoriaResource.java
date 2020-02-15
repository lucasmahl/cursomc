package com.lucasmahl.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

import com.lucasmahl.cursomc.domain.Categoria;
import com.lucasmahl.cursomc.dto.CategoriaDTO;
import com.lucasmahl.cursomc.services.CategoriaService;

//Resource pq é um controlador Rest
@RestController // diretiva do spring boot
@RequestMapping(value = "/categorias") // endpont
public class CategoriaResource {

	@Autowired //pra instanciar automaticamente pelo spring
	private CategoriaService service;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET) // GET, verbo http
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {//PathVariablepq o id da url vai vir pra o da variavel
		//ResponseEntity tipo do spring, q encapsula varias informações de um http p/ um serviço rest
		//ResponseEntity<?> pq pode ser qualquer tipo, até nulo
		
		Categoria obj = service.find(id);
		
		return ResponseEntity.ok().body(obj);
	}
	
	//metodo pra receber categoria em json e inseri-la no banco
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Categoria obj){//@RequestBody pra construir a categoria através do json
		obj = service.insert(obj);//tem "obj =" pq save do repository retorna obj
		
		//uri de resposta, retorna a uri q foi salva, tipo: http://localhost:8080/categorias/3
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")//fromCurrentRequest pega url q foi usada pra inserir
				.buildAndExpand(obj.getId()).toUri();//buildAndExpand pra atribuir valor
		
		return ResponseEntity.created(uri).build();//created gera o cód 201, de criação q foi feita com sucesso		
	}
	
	//pra fazer alteração
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)//pathvariable abaixo pq tmbm vai receber parametro da url
	public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer id){//@RequestBody pra construir a categoria através do json,
		obj.setId(id);//por garantia, iguala com o id q foi recebido (boa pratica)
		
		obj = service.update(obj);
		
		return ResponseEntity.noContent().build(); //nocontent = conteudo vazio
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE) // DELETE, verbo http
	public ResponseEntity<Void> delete(@PathVariable Integer id) {//PathVariablepq o id da url vai vir pra o da variavel
		//ResponseEntity tipo do spring, q encapsula varias informações de um http p/ um serviço rest
		//ResponseEntity<?> pq pode ser qualquer tipo, até nulo
		
		service.delete(id);
		
		return ResponseEntity.noContent().build(); //se deletou com sucesso, retorna a resposta, q deu ok, sem conteúdo
	}
	
	@RequestMapping(method = RequestMethod.GET) // GET, verbo http
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		//ResponseEntity tipo do spring, q encapsula varias informações de um http p/ um serviço rest
		//ResponseEntity<?> pq pode ser qualquer tipo, até nulo
		
		List<Categoria> list = service.findAll(); //função do service
		List<CategoriaDTO> listDTO = list.stream().map(obj->new CategoriaDTO(obj))//map faz uma operação pra cada elemento da lista
									.collect(Collectors.toList());//volta stream pra lista
		return ResponseEntity.ok().body(listDTO);
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET) // GET, verbo http //value = http://localhost:8080/categorias/page
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0")Integer page,//RequestParam pq é um parametro opcional
			@RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome")String orderBy,//ordenado por nome
			@RequestParam(value = "direction", defaultValue = "ASC")String direction) {
		//ResponseEntity tipo do spring, q encapsula varias informações de um http p/ um serviço rest
		//ResponseEntity<?> pq pode ser qualquer tipo, até nulo
		
		Page<Categoria> list = service.findPage(page, linesPerPage, orderBy, direction); //função do service
		Page<CategoriaDTO> listDTO = list.map(obj->new CategoriaDTO(obj)); //map faz uma operação pra cada elemento da lista
		
		return ResponseEntity.ok().body(listDTO);
	}
}
