package com.farias.webflux.controller;

import javax.validation.Valid;

import com.farias.webflux.model.Usuario;
import com.farias.webflux.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
public class UsuarioController {


    @Autowired
    private UsuarioRepository usuarioRepository;

    
    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<Usuario> createUser(@Valid @RequestBody Usuario user) {
        return usuarioRepository.save(user);
    }

    @GetMapping("/")
    @ResponseStatus(code = HttpStatus.OK)
    public Flux<Usuario> users() {
        return usuarioRepository.findAll();
    
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<ResponseEntity<Usuario>> user(@PathVariable("id") Integer id)  {
        return  usuarioRepository.findById(id)
                                 .map(user -> ResponseEntity.ok()
                                                            .body(user))
                                 .defaultIfEmpty(ResponseEntity.notFound()
                                                                .build());
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Usuario>> userUpdate(@PathVariable("id") Integer id, @Valid @RequestBody Usuario usuario) {

        return usuarioRepository.findById(id)
                                 .flatMap(user -> {
                                    user.setName(usuario.getName());
                                    user.setStack(usuario.getStack());
                                    return usuarioRepository.save(user);
                               }).map(user -> new ResponseEntity<>(user, HttpStatus.OK) )
                                 .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND) );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> userDelete(@PathVariable("id") Integer id) {
        return usuarioRepository.findById(id)
                                .flatMap(user -> {
                                    usuarioRepository.delete(user)
                                                    .then(Mono.just(new ResponseEntity(HttpStatus.OK)))
                                }).defaultIfEmpty(new ResponseEntity(HttpStatus.NOT_FOUND));
    }


    @GetMapping(value ="/stream/users", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public Flux<Usuario> usersAll() {
        return usuarioRepository.findAll();
    }


}
