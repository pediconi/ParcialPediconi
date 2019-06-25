package com.parcial.parcial.controllers;

import com.parcial.parcial.models.Comentario;
import com.parcial.parcial.models.Publicacion;
import com.parcial.parcial.models.Usuario;
import com.parcial.parcial.service.ListadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/allContent")
public class ListadoController {

    @Autowired
    private ListadoService listadoService;

    @GetMapping("/async")
    public ResponseEntity<?> getAsync() {

        CompletableFuture<List<Usuario>> listaUsuarios = listadoService.usuarios();
        CompletableFuture<List<Publicacion>> listaPublicaciones = listadoService.publicaciones();
        CompletableFuture<List<Comentario>> listaComentarios = listadoService.comentarios();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(listaComentarios.join());   // No me alcanzo el tiempo, intentando retornar las 3 listas.
    }

}
