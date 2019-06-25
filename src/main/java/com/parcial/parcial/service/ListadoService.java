package com.parcial.parcial.service;

import com.parcial.parcial.controllers.ComentarioController;
import com.parcial.parcial.controllers.PublicacionController;
import com.parcial.parcial.controllers.UsuarioController;
import com.parcial.parcial.models.Comentario;
import com.parcial.parcial.models.Publicacion;
import com.parcial.parcial.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ListadoService {

    @Autowired
    private UsuarioController usuarioController;
    @Autowired
    private PublicacionController publicacionController;
    @Autowired
    private ComentarioController comentarioController;

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<List<Usuario>> usuarios() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(usuarioController.getAll());
    }

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<List<Publicacion>> publicaciones() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(publicacionController.getAll());
    }

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<List<Comentario>> comentarios() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(comentarioController.getAll());
    }
}
