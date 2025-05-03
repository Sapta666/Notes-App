package com.sapta.portfolio.apps.notes.research;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;

import com.sapta.portfolio.apps.notes.app.utils.HelperUtils;
import com.sapta.portfolio.apps.notes.app.utils.JWTUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTTest101 {
	
	private static String secretKey = "22051996";
		
	private static JWTUtils jwtUtils = new JWTUtils();
	
	public static void main(String[] args) {		
		String base64Key = Base64.getEncoder().encodeToString(secretKey.getBytes());
		final String username = "India Is Great!";
		
		String token = Jwts.builder()
						.setSubject(username)
						.signWith(SignatureAlgorithm.HS256, secretKey)						
						.compact();
		
		token = jwtUtils.getJWTWithUsername(username);
		
		System.out.println(token);
		
		Claims claims = Jwts.parser()								
				.setSigningKey(secretKey)
				.parseClaimsJws(token)
				.getBody();
		
		System.out.println(claims.getSubject());
								
		
	}

}
