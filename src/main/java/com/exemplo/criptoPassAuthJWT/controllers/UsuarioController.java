package com.exemplo.criptoPassAuthJWT.controllers;

//import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.exemplo.criptoPassAuthJWT.entities.UsuarioEntity;
import com.exemplo.criptoPassAuthJWT.repositories.UsuarioRepository;

import java.util.List;

import java.util.*;



@RestController
@RequestMapping("api/usuario")
public class UsuarioController {
	
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	private final PasswordEncoder encoder;


    UsuarioController(PasswordEncoder encoder) {
        this.encoder = encoder;
    }	
	
	
	@GetMapping("/listarTodos")
	public List<UsuarioEntity> listarUses(){
		
		return usuarioRepository.findAll();		
	}
	
	
	@PostMapping("/salvar")
	public void salvar(@RequestBody UsuarioEntity usuario) {
		usuario.setPassword(encoder.encode(usuario.getPassword()));
		usuarioRepository.save(usuario);
	}
	
	
	@PutMapping("/alterar")
	public void alterar(@RequestBody UsuarioEntity usuario) {
		if (usuario.getId()  > 0) {
			usuarioRepository.save(usuario);
		}
	}	
	
	
	@DeleteMapping("/deletar")
	public void Excluir(@RequestBody UsuarioEntity usuario) {
		if (usuario.getId()  > 0) {
			usuarioRepository.delete(usuario);
			}
	}   
	
	
	@GetMapping("/validarSenha")
	public ResponseEntity<Boolean> validarSenha(@RequestParam String login, @RequestParam String password ){
		
		
		//Optional<UsuarioEntity> optUsuario = usuarioRepository.findByLogin(login);
		
		Optional<UsuarioEntity> optUsuario = usuarioRepository.findByLogin(login);
		
		if (optUsuario.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
		}else {
			
			//boolean valid = false;
			UsuarioEntity usuarioEntity = optUsuario.get();
			boolean valid = encoder.matches(password, usuarioEntity.getPassword());
			
			HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
			
			return ResponseEntity.status(status).body(valid);
		}
				
						
	}
	
		
	

}
