package com.lucasmahl.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasmahl.cursomc.domain.Pedido;
import com.lucasmahl.cursomc.repositories.PedidoRepository;
import com.lucasmahl.cursomc.services.exceptions.ObjectNotFoundException;

//REGRAS DE NEGOCIO
@Service
public class PedidoService {
	@Autowired // vai ser automaticamente instanciada pelo spring
	private PedidoRepository repo;

	//busca Pedido pelo Id, e retorna exceção caso ele não exista
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

}
