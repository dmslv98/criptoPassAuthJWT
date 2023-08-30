package com.exemplo.criptoPassAuthJWT.details;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.exemplo.criptoPassAuthJWT.entities.UsuarioEntity;


public class DetalheUsuarioData implements UserDetails  {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//@Autowired
	//private UsuarioEntity usuario;
	
	private final Optional<UsuarioEntity> usuario;
	
	public DetalheUsuarioData(Optional<UsuarioEntity> usuario) {
		this.usuario = usuario;		
	}
	
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		//return null;
		return new ArrayList<>();
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return usuario.orElse(new UsuarioEntity()).getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return usuario.orElse(new UsuarioEntity()).getLogin();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	

}
