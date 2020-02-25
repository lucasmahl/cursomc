package com.lucasmahl.cursomc.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;

	public String generateToken(String username) {

		return Jwts.builder().setSubject(username)// usuario
				.setExpiration(new Date(System.currentTimeMillis() + expiration))// horaio atual + tempo de expiração
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())// como será assinado o token
				.compact();
	}

	public boolean tokenValido(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			String username = claims.getSubject();
			Date expirationDate = claims.getExpiration(); // pra testar se token expirou
			Date now = new Date(System.currentTimeMillis());// pra testar se token expirou
			if (username != null && expirationDate != null && now.before(expirationDate)) {
				return true;
			}
		}
		return false;
	}
	
	public String getUserName(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			return claims.getSubject();
		}
		return null;
	}

	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		}catch (Exception e) {
			return null;
		}
	}
}
