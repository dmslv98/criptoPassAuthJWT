package com.exemplo.criptoPassAuthJWT.security;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTValidFilter extends BasicAuthenticationFilter {
	
	public static final String HEADER_ATRIBUT = "Authorization";
	public static final String ATRIBUT_PREFIX = "Bearer ";
	
	

	public JWTValidFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doFilterInternal(
			HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain chain)
			throws IOException, ServletException {		
		
			String atribut = request.getHeader(HEADER_ATRIBUT);
			
			if (atribut == null) {
				chain.doFilter(request, response);
				return;
			}
			
			if (!atribut.startsWith(ATRIBUT_PREFIX)) {
				chain.doFilter(request, response);
				return;
			}
			
			
			String token = atribut.replace(ATRIBUT_PREFIX,"");			
			UsernamePasswordAuthenticationToken  authenticationToken = getAuthenticationToken(token); 		
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			chain.doFilter(request, response);
		
		
		
		// TODO Auto-generated method stub
		//super.doFilterInternal(request, response, chain);
	}
	
	
	private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
		
		String usuario = JWT.require(Algorithm.HMAC512(JWTAutenticFilter.TOKEM_SENHA))
				.build()
				.verify(token)
				.getSubject();
		
		if (usuario == null ) {
			return null;
		}else{
			return new UsernamePasswordAuthenticationToken(usuario, null, new ArrayList<>());
		}
				
	}
	
	
	
	
}
