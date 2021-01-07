package com.farias.webflux.repository;

import com.farias.webflux.model.Usuario;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends ReactiveMongoRepository<Usuario, String> {
    
}
