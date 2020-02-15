package com.lucasmahl.cursomc.dto;

import java.io.Serializable;

import com.lucasmahl.cursomc.domain.Categoria;

public class CategoriaDTO implements Serializable {// Serializable faz com q os objetos da classe virem bytes, pra
													// armazenamento e etc.
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;

	public CategoriaDTO() {// importante Construtor vazio, pq algumas bibliotecas utilizam isto

	}

	public CategoriaDTO(Categoria obj) {// Construtor responsável por instanciar o DTO a partir de Categoria
		id = obj.getId();
		nome = obj.getNome();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
