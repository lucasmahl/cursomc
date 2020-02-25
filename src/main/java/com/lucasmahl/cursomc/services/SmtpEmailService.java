package com.lucasmahl.cursomc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SmtpEmailService extends AbstractEmailService{

	@Autowired
	private MailSender mailSender;
	
	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class); //pra mostrar email no logger do servidor

	@Override
	public void sendEmail(SimpleMailMessage msg) {
		// pra imprimir um log de mensagens
		LOG.info("Enviando de email.");
		mailSender.send(msg);
		LOG.info("Email enviado.");
		
	}

}
