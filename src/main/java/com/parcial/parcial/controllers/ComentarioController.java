package com.parcial.parcial.controllers;

import com.parcial.parcial.models.Comentario;
import com.parcial.parcial.models.Publicacion;
import com.parcial.parcial.repositories.ComentarioRepository;
import com.parcial.parcial.repositories.PublicacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/comentario")  // le indico que es una controladora osea cuando ponga localhost/persona viene a esta clase
@RestController
public class ComentarioController {

    private final String PUBLICACION_NOT_FOUND = "Publicacion no encontrado";
    private final String COMENTARIO_NOT_FOUND = "Comentario con id: %s no existe";

    @Autowired
    private ComentarioRepository comentarioRepository;
    @Autowired
    private PublicacionRepository publicacionRepository;

    @PostMapping("/{id_publicacion}")     // a la persona que recibe este metodo x post la guarda en la lista
    public void addComentario(@PathVariable Integer id_publicacion, @RequestBody Comentario c) {

        Publicacion p = publicacionRepository.findById(id_publicacion) // me traigo el equipo lo busco x id
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, String.format(PUBLICACION_NOT_FOUND,id_publicacion)));

        c.setPublicacion(p);   // como en el json cuando cargo un jugador no pongo su equipo(x el json ignore de la clase jugador), lo tengo q setear aca
        p.getComentarios().add(c);     // a ese equipo le añado el jugador a su lista y guardo

        comentarioRepository.save(c);
        publicacionRepository.save(p);
    }

    @GetMapping("")   // peticiones get(desde postman) envia una peticion y llama a este metodo
    public List<Comentario> getAll(){
        return comentarioRepository.findAll();              // levanto los jugadores de la bd y los mapeo para mostrarlos
    }

    @GetMapping("/{id}")
    public Comentario getComentariorById(@PathVariable Integer id){
            return comentarioRepository.findById(id)
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, String.format(COMENTARIO_NOT_FOUND,id)));
    }


    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id){
        comentarioRepository.deleteById(id);
    }

}
