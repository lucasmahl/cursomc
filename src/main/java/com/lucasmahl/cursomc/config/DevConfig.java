package com.lucasmahl.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.lucasmahl.cursomc.services.DBService;

@Configuration //@Configuration pq é um arquivo de configuração
@Profile("dev") //pra indicar q é especifica do profile de teste
public class DevConfig {

	@Autowired
	private DBService dbService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")//pega o valor dessa da classe applicatio-dev.properties e armazena em strategy
	private String strategy;
	
	@Bean
	public boolean instantiateDataBase() throws ParseException {
		if (!"create".equals(strategy)) {
			return false;
		}
		
		dbService.instantiateTestDataBase();
		
		return true;
	}
}
