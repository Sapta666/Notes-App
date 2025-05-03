package com.sapta.portfolio.apps.notes.app;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.OperatorInstanceof;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sapta.portfolio.apps.notes.app.utils.HelperUtils;
import com.sapta.portfolio.apps.notes.app.utils.JWTUtils;

import io.jsonwebtoken.ExpiredJwtException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapta.portfolio.apps.notes.app.AppUserDetailService;
import com.sapta.portfolio.apps.notes.app.enums.SuccessFailedEnum;
import com.sapta.portfolio.apps.notes.app.models.common.BasicResponseDto;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class JWTFilter extends OncePerRequestFilter {

	@Autowired private JWTUtils jwtUtils;
	@Autowired private AppUserDetailService appUserDetailsService;
	@Autowired private HelperUtils helperUtils;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwtToken = "";
		String temp = request.getHeader("authorization");
		if(request.getHeader("authorization") != null)		
			jwtToken = request.getHeader("authorization").split(" ")[1];
		
		Iterator<String> it = request.getHeaderNames().asIterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}

		try {
			if (jwtToken.length() > 0) {
				String usernameEncrypted = jwtUtils.extractJWTPayload_Username(jwtToken);

				UserDetails userDetails = this.appUserDetailsService
						.loadUserByUsername(usernameEncrypted);
				if (userDetails != null) {
					UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
							usernameEncrypted, null,userDetails.getAuthorities());
//					token.setDetails(userDetails);
					token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					if (SecurityContextHolder.getContext().getAuthentication() == null)
						SecurityContextHolder.getContext().setAuthentication(token);											
				}

			}
			filterChain.doFilter(request, response);
		} catch (UsernameNotFoundException | NullPointerException e) {
			response.sendError(400, "Invalid JWT Token! "+e.getMessage());
			e.printStackTrace();
		} catch(ExpiredJwtException e) {
			
			BasicResponseDto errorResponse = new BasicResponseDto(SuccessFailedEnum.FAILED.toString(), "JWT token has expired",true);
			
			// Handle the expired token exception
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
		}


	}

}
