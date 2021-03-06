package com.lucasmahl.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.lucasmahl.cursomc.domain.Cliente;
import com.lucasmahl.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService{
	
	@Value("${default.sender}")
	private String sender; //email padrão da aplicação (application.properties)
	
	
	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
	}

	//protected pq pode ser acessado por subclasses, mas não por usuários da classe (controladores de serviços)
	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		
		sm.setTo(obj.getCliente().getEmail());//destinatario
		sm.setFrom(sender);//email padrão da aplicação (application.properties)
		sm.setSubject("Pedido confirmado! Código: " + obj.getId()); //Assunto
		sm.setSentDate(new Date(System.currentTimeMillis())); //pra garantir q a data será do meu servidor
		sm.setText(obj.toString()); //corpo do pedido
		return sm;
		
	}
	
	@Override
	public void sendNewPassword(Cliente cliente, String newPass) {
		SimpleMailMessage sm = prepareNewPasswordEmail(cliente, newPass);
		sendEmail(sm);
	}

	//protected pra subclasse poder sobrepor, se necessário
	protected SimpleMailMessage prepareNewPasswordEmail(Cliente cliente, String newPass) {
		SimpleMailMessage sm = new SimpleMailMessage();
		
		sm.setTo(cliente.getEmail());//destinatario
		sm.setFrom(sender);//email padrão da aplicação (application.properties)
		sm.setSubject("Solicitaçao de nova senha."); //Assunto
		sm.setSentDate(new Date(System.currentTimeMillis())); //pra garantir q a data será do meu servidor
		sm.setText("Nova senha: " + newPass); //corpo do pedido
		return sm;
	}
}
