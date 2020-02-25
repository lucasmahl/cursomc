package com.lucasmahl.cursomc.domain.enums;

public enum Perfil {

	ADMIN(1, "ROLE_ADMIN"), //ROLE É EXIGENCIA DO SPRING SECURITY
	CLIENTE(2, "ROLE_CLIENTE");

	private int cod;
	private String descricao;

	// construtor do tipo enum é private
	private Perfil(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	// static por q deve ser possível de ser executada, mesmo sem instanciar objetos
	public static Perfil toEnum(Integer cod) {// converte para enum
		if (cod == null) {
			return null;
		}

		for (Perfil x : Perfil.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Id inválido " + cod + ".");
	}

}
