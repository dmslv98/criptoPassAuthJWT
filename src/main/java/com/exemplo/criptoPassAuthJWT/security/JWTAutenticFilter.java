package com.exemplo.criptoPassAuthJWT.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.exemplo.criptoPassAuthJWT.details.DetalheUsuarioData;
import com.exemplo.criptoPassAuthJWT.entities.UsuarioEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



public class JWTAutenticFilter extends UsernamePasswordAuthenticationFilter {
	
	public static final int TOKEN_EXPIRACAO = 600_000;	
	public static final String TOKEM_SENHA = "afdee0e1-1956-4257-be9a-3b8bd335879f";  //NÃO CONFIGURAR AQUI EM PRODUÇÃO
	
	
	private final AuthenticationManager authenticationManager;
	
	public JWTAutenticFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;		
	}
	
	
	//public Authentication
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, 
												HttpServletResponse response) throws AuthenticationException {
		// TODO Auto-generated method stub		
		
			try {
				UsuarioEntity usuario = new ObjectMapper()
						.readValue(request.getInputStream(), UsuarioEntity.class);
				
				return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
						usuario.getLogin(),
						usuario.getPassword(),
						new ArrayList<>()					
				));		
						
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException("Falha ao autenticar usuário ", e);
			}
		
		}
		
		@Override
		protected void successfulAuthentication(
				HttpServletRequest  request, 
				HttpServletResponse response, 
				FilterChain chain,
				Authentication authResult) throws IOException, ServletException {		

		   DetalheUsuarioData usuarioData = (DetalheUsuarioData) authResult.getPrincipal();
		   
		   String  token = JWT.create()
				   .withSubject(usuarioData.getUsername()) 
				   .withExpiresAt(new Date(System.currentTimeMillis()+TOKEN_EXPIRACAO))
				   .sign(Algorithm.HMAC512(TOKEM_SENHA));
		   
		   response.getWriter().write(token);
		   response.getWriter().flush();
	    }
	 

}
