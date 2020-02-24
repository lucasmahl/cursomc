package com.lucasmahl.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.lucasmahl.cursomc.services.DBService;

@Configuration //@Configuration pq é um arquivo de configuração
@Profile("test") //pra indicar q é especifica do profile de teste
public class TestConfig {

	@Autowired
	private DBService dbService;
	
	@Bean
	public boolean instantiateDataBase() throws ParseException {
		dbService.instantiateTestDataBase();
		
		return true;
	}
}
