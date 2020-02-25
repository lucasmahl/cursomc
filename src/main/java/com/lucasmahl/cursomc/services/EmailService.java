package com.lucasmahl.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.lucasmahl.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);//email de confirmação de pedido
	void sendEmail(SimpleMailMessage msg);
}
