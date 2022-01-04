package com.desafio24h.aceito.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.desafio24h.aceito.model.UsuarioModel;

@Repository
public interface UsuarioRepository extends MongoRepository<UsuarioModel, Long> {
	
	UsuarioModel findById(long id);
	UsuarioModel findByCpf(String cpf);

}
