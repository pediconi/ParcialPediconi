package com.parcial.parcial.controllers;


import com.parcial.parcial.models.Publicacion;
import com.parcial.parcial.models.Usuario;
import com.parcial.parcial.repositories.PublicacionRepository;
import com.parcial.parcial.repositories.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/publicacion")  // le indico que es una controladora osea cuando ponga localhost/persona viene a esta clase
@RestController
public class PublicacionController {

    private final String USUARIO_NOT_FOUND = "Usuario no encontrado";
    private final String PUBLICACION_NOT_FOUND = "Publicacion con id: %s no existe";

    @Autowired
    private PublicacionRepository publicacionRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/{id_usuario}")     // a la persona que recibe este metodo x post la guarda en la lista
    public void addPublicacion(@PathVariable Integer id_usuario, @RequestBody Publicacion p) {

        Usuario u = usuarioRepository.findById(id_usuario) // me traigo el equipo lo busco x id
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, String.format(USUARIO_NOT_FOUND,id_usuario)));

        p.setUsuario(u);   // como en el json cuando cargo un jugador no pongo su equipo(x el json ignore de la clase jugador), lo tengo q setear aca
        u.getPublicaciones().add(p);     // a ese equipo le añado el jugador a su lista y guardo

        publicacionRepository.save(p);
        usuarioRepository.save(u);
    }

    @GetMapping("")   // peticiones get(desde postman) envia una peticion y llama a este metodo
    public List<Publicacion> getAll(){
        return publicacionRepository.findAll();               // levanto los jugadores de la bd y los mapeo para mostrarlos
    }

    @GetMapping("/{id}")
    public Publicacion getPublicacionById(@PathVariable Integer id){
        return publicacionRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, String.format(PUBLICACION_NOT_FOUND,id)));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id){
        publicacionRepository.deleteById(id);
    }

    @Scheduled(fixedDelayString = "${my.property.fixed.delay.seconds}000")    // 1000ms = 1 seg
    public void eliminaComentarios() {
        List<Publicacion> publicacionse = publicacionRepository.findAll();
        for(Publicacion p : publicacionse){

            p.getComentarios().removeIf(x -> (x.getFecha().plusMinutes(1).isBefore(LocalDateTime.now())));
            publicacionRepository.saveAndFlush(p);
        }
    }

}
