package com.sapta.portfolio.apps.notes.app.utils;

import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTUtils {
	
	private final String secretKey = "22051996";
	
	public String extractJWTPayload_Username(String jwtStr) {				
		Claims claims = Jwts.parser()
					.setSigningKey(secretKey)
					.parseClaimsJws(jwtStr)
					.getBody();		
		
		return claims.getSubject();
	}
	
	public String getJWTWithUsername(String username) {
		String token = Jwts.builder()
				 	.setIssuedAt(new Date())
	                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
					.signWith(SignatureAlgorithm.HS256, this.secretKey)
					.setSubject(username)
					.compact();
		
		return token;
	}
		
}
