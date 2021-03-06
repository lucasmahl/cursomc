package com.lucasmahl.cursomc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.lucasmahl.cursomc.security.JWTAuthenticationFilter;
import com.lucasmahl.cursomc.security.JWTAuthorizationFilter;
import com.lucasmahl.cursomc.security.JWTUtil;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//permite préautorização nos endpoints
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment env;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	// links q estarão liberados
	private static final String[] PUBLIC_MATCHERS = { 
			"/h2-console/**"
	};
	
	// links q estarão liberados somente leitura
	private static final String[] PUBLIC_MATCHERS_GET = { 
			"/produtos/**",
			"/categorias/**"
	};
	
	// para cadastro de novo usuario
	private static final String[] PUBLIC_MATCHERS_POST = { 
			"/clientes/**",
			"/auth/forgot/**"
	};
		

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {//pega os perfis ativos, se for test, acessa o h2
			http.headers().frameOptions().disable();
		}
		
		http.cors().and().csrf().disable();//se tiver CorsConfigurationSource defini, como abaixo, estão as configs serão aplicadas
		http.authorizeRequests()
		.antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll() 
		.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll() //liberado apenas pro metodo GET
		.antMatchers(PUBLIC_MATCHERS).permitAll()// todos caminhos q estiverem no vetor
																			// PUBLIC_MATCHERS, serão permitido
				.anyRequest().authenticated();// pra todo resto, será exigida autenticação
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//pra assegurar q não será criada sessão de usuário
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth)throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	// permite acesso de multiplas fontes, pra todos os caminhos
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();		
	}
}
