package com.lucasmahl.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lucasmahl.cursomc.domain.Cliente;
import com.lucasmahl.cursomc.repositories.ClienteRepository;
import com.lucasmahl.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private EmailService emailService;

	private Random rand = new Random();

	public void sendNewPassword(String email) {

		Cliente cliente = clienteRepository.findByEmail(email);

		if (cliente == null) {
			throw new ObjectNotFoundException("Email não encontrado.");
		}

		String newPass = newPassword();
		cliente.setSenha(pe.encode(newPass));

		clienteRepository.save(cliente);

		// envia email
		emailService.sendNewPassword(cliente, newPass);
	}

	private String newPassword() {
		// 10 caracteres, digitos ou letras
		char[] vet = new char[10];

		for (int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}

		return new String(vet);// retorna uma String recebendo o vet como argumento
	}

	private char randomChar() {
		int opt = rand.nextInt(3);// 0,1,2
		if (opt == 0) {// gera digito
			// https://unicode-table.com/pt/
			return (char) (rand.nextInt(10) + 48);
		} else if (opt == 1) {// gera letra maiuscula
			return (char) (rand.nextInt(26) + 65);
		} else {// gera letra minuscula
			return (char) (rand.nextInt(10) + 97);
		}
	}
}
