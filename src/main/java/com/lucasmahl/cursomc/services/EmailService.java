package com.lucasmahl.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.lucasmahl.cursomc.domain.Pedido;

//padrao strategy
public interface EmailService {
	//sendOrderConfirmationEmail usa sendEmail, e antes de enviar email tem q preparar SimpleMailMessage a partir de um pedido
	void sendOrderConfirmationEmail(Pedido obj);//email de confirmação de pedido
	void sendEmail(SimpleMailMessage msg);
}
