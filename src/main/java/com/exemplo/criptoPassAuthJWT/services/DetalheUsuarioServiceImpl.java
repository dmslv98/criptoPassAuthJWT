package com.exemplo.criptoPassAuthJWT.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.exemplo.criptoPassAuthJWT.details.DetalheUsuarioData;
import com.exemplo.criptoPassAuthJWT.entities.UsuarioEntity;
import com.exemplo.criptoPassAuthJWT.repositories.UsuarioRepository;

@Component
public class DetalheUsuarioServiceImpl implements UserDetailsService{
	
	
	private final UsuarioRepository repository;
	
	public DetalheUsuarioServiceImpl(UsuarioRepository repository) {
		this.repository = repository;		
	}
	
	//Consulta o login pelo nome do usuario

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		Optional<UsuarioEntity> usuario = repository.findByLogin(username);
		
		if(usuario.isEmpty()) {
			throw new UsernameNotFoundException("Usuario [" + username + "] não encontrado" );
		}
		return new DetalheUsuarioData(usuario);
		
	}

}
