package com.lucasmahl.cursomc.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

//clase de associação
@Entity
public class ItemPedido implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonIgnore // pra evitar loop infinito, não será serializado, @JsonIgnore pq é chave
				// composta e são 2 níveis de acesso
	@EmbeddedId // @EmbeddedId por ser um id embutido em um tipo auxiliar
	private ItemPedidoPK id = new ItemPedidoPK(); // ItemPedidoPK é clase auxiliar, id é chave composta

	private Double desconto;
	private Integer quantidade;
	private Double preco;

	public ItemPedido() {

	}

	// deve ser trocado ItemPedidoPK id por Pedido pedido, Produto produto
	public ItemPedido(Pedido pedido, Produto produto, Double desconto, Integer quantidade, Double preco) {
		super();
		id.setPedido(pedido);
		id.setProduto(produto);
		this.desconto = desconto;
		this.quantidade = quantidade;
		this.preco = preco;
	}

	public double getSubTotal() {// get pra q seja reconhecido pelo json e serializado
		return (preco - desconto) * quantidade;
	}

	@JsonIgnore // pra evitar loop da referencia ciclica
	public Pedido getPedido() {
		return id.getPedido();
	}

	public void setPedido(Pedido pedido) {
		id.setPedido(pedido);
	}

	// @JsonIgnore //pra evitar loop da referencia ciclica
	public Produto getProduto() {
		return id.getProduto();
	}

	public void setProduto(Produto produto) {
		id.setProduto(produto);
	}

	public ItemPedidoPK getId() {
		return id;
	}

	public void setId(ItemPedidoPK id) {
		this.id = id;
	}

	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemPedido other = (ItemPedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		
		StringBuilder builder = new StringBuilder();
		builder.append(getProduto().getNome());
		builder.append(", Qtde: ");
		builder.append(getQuantidade());
		builder.append(", Preço unitário: ");
		builder.append(nf.format(getPreco()));
		builder.append(", subtotal: ");
		builder.append(nf.format(getSubTotal()));
		builder.append("\n");
		return builder.toString();
	}

}
