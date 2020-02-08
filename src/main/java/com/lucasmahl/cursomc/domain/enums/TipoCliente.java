package com.lucasmahl.cursomc.domain.enums;

public enum TipoCliente {
	PESSOAFISICA(1, "Pessoa Física"), // (código, descrição)
	PESSOAJURIDICA(2, "Pessoa Jurídica");

	private int cod;
	private String descricao;

	// construtor do tipo enum é private
	private TipoCliente(int cod, String descricao) {
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
	public static TipoCliente toEnum(Integer cod) {// converte para enum
		if (cod == null) {
			return null;
		}

		for (TipoCliente x : TipoCliente.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Id inválido " + cod + ".");
	}
}
