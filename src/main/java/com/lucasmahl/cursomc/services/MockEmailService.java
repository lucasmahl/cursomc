package com.lucasmahl.cursomc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class MockEmailService extends AbstractEmailService {

	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class); //pra mostrar email no logger do servidor
	
	@Override
	public void sendEmail(SimpleMailMessage msg) {
		// pra imprimir um log de mensagens
		LOG.info("Simulando envio de email.");
		LOG.info(msg.toString());//imprime a mensagem
		LOG.info("Email enviado.");
		
	}

}
