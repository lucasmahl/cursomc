package com.lucasmahl.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.lucasmahl.cursomc.domain.Cliente;
import com.lucasmahl.cursomc.services.validation.ClienteUpdate;

@ClienteUpdate // pra verificar se existe outro id, com o mesmo e-mail, ao alterar email
public class ClienteDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotEmpty(message = "Nome é preenchimento obrigatório") //@NotEmpty só se aplica pra String
	@Length(min=5, max=120, message = "O tamanho deve ser maior que 5 caracteres.")
	private String nome;
	
	@NotEmpty(message = "Email é preenchimento obrigatório") //@NotEmpty só se aplica pra String
	@Email(message = "Email inválido.")
	private String email;
	
	public ClienteDTO() {

	}
	
	public ClienteDTO(Cliente obj) {
		id = obj.getId();
		nome = obj.getNome();
		email = obj.getEmail();
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
