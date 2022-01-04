package com.desafio24h.aceito.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio24h.aceito.domain.model.Usuario;
import com.desafio24h.aceito.model.UsuarioModel;
import com.desafio24h.aceito.repository.UsuarioRepository;
import com.desafio24h.aceito.service.UsuarioService;

@RestController
@RequestMapping("/api")

public class UsuarioController {

	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	
	@GetMapping
	public List<UsuarioModel> listaUsuarios() {
		return usuarioService.findAll();
	
	}
	@GetMapping("/{cpf}")
	public ResponseEntity<?> listaUnicoUser(@PathVariable (name = "cpf") String cpf ){
		try {
			
			return new ResponseEntity<UsuarioModel>(usuarioService.findByCpf(cpf), HttpStatus.OK);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
		}
	}
	
	@PostMapping
	public ResponseEntity<?> criaUsuarios(@RequestBody Usuario usuario){
		try {
			usuarioService.criar(usuario);
			return ResponseEntity.status(HttpStatus.OK).body("Usuário cadastrado.");
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao cadastrar usuario" + e);
		}
		
	}
	
}
