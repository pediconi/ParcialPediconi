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
    private ListadoService consummerService;

    @GetMapping("/async")
    public ResponseEntity<?> getAsync() {

        CompletableFuture<List<Usuario>> resultMethodOne = consummerService.usuarios();  // duerme 5 seg, retorna 5 (al ser async entran los 2 en ejec, no se queda esperando la resp del primero)
        CompletableFuture<List<Publicacion>> resultMethodTwo = consummerService.publicaciones();  // duerme 2 seg , retorna 2
        CompletableFuture<List<Comentario>> resultMethodThree = consummerService.comentarios();



        return ResponseEntity
                .status(HttpStatus.OK)
                .body(resultMethodOne.join());
    }

}
