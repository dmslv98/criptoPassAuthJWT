package com.exemplo.criptoPassAuthJWT.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exemplo.criptoPassAuthJWT.entities.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer>   {

	public Optional<UsuarioEntity> findByLogin(String login);
	

}
