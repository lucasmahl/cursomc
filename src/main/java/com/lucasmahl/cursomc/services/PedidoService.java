package com.lucasmahl.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucasmahl.cursomc.domain.ItemPedido;
import com.lucasmahl.cursomc.domain.PagamentoComBoleto;
import com.lucasmahl.cursomc.domain.Pedido;
import com.lucasmahl.cursomc.domain.enums.EstadoPagamento;
import com.lucasmahl.cursomc.repositories.ItemPedidoRepository;
import com.lucasmahl.cursomc.repositories.PagamentoRepository;
import com.lucasmahl.cursomc.repositories.PedidoRepository;
import com.lucasmahl.cursomc.services.exceptions.ObjectNotFoundException;

//REGRAS DE NEGOCIO
@Service
public class PedidoService {
	@Autowired // vai ser automaticamente instanciada pelo spring
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	//busca Pedido pelo Id, e retorna exceção caso ele não exista
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);//pra garantir q realmente está sendo inserido um novo pedido
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj); //pagamento tem q conhecer seu pedido
		
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		
		//salvando
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		
		for (ItemPedido ip: obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco( produtoService.find(ip.getProduto().getId()).getPreco() );//copia o preco do produto
			ip.setPedido(obj); //associa o tem de pedido, com pedido q está sendo inserido
		}
		
		itemPedidoRepository.saveAll(obj.getItens()); //repository é capaz de salvar uma lista
		return obj;
	}
}
