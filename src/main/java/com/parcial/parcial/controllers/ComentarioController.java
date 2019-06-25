package com.parcial.parcial.controllers;

import com.parcial.parcial.models.Comentario;
import com.parcial.parcial.models.Publicacion;
import com.parcial.parcial.repositories.ComentarioRepository;
import com.parcial.parcial.repositories.PublicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import java.util.List;

@RequestMapping("/comentario")
@RestController
public class ComentarioController {

    private final String PUBLICACION_NOT_FOUND = "Publicacion no encontrado";
    private final String COMENTARIO_NOT_FOUND = "Comentario con id: %s no existe";

    @Autowired
    private ComentarioRepository comentarioRepository;
    @Autowired
    private PublicacionRepository publicacionRepository;

    @PostMapping("/{id_publicacion}")
    public void addComentario(@PathVariable Integer id_publicacion, @RequestBody Comentario c) {

        Publicacion p = publicacionRepository.findById(id_publicacion)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, String.format(PUBLICACION_NOT_FOUND,id_publicacion)));

        c.setPublicacion(p);
        p.getComentarios().add(c);

        comentarioRepository.save(c);
        publicacionRepository.save(p);
    }

    @GetMapping("")
    public List<Comentario> getAll(){
        return comentarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public Comentario getComentariorById(@PathVariable Integer id){
            return comentarioRepository.findById(id)
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, String.format(COMENTARIO_NOT_FOUND,id)));
    }

    @PutMapping("/{id}")
    public void updateById(@RequestBody Comentario c, @PathVariable Integer id){
        Comentario buscado = comentarioRepository.getOne(id);

        buscado.setDescripcion(c.getDescripcion());
        buscado.setNombre(c.getNombre());
        comentarioRepository.saveAndFlush(buscado);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id){
        comentarioRepository.deleteById(id);
    }

}
