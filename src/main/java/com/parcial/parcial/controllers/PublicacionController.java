package com.parcial.parcial.controllers;


import com.parcial.parcial.models.Publicacion;
import com.parcial.parcial.models.Usuario;
import com.parcial.parcial.repositories.ComentariosPorPublicacion;
import com.parcial.parcial.repositories.PublicacionRepository;
import com.parcial.parcial.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/publicacion")
@RestController
public class PublicacionController {

    private final String USUARIO_NOT_FOUND = "Usuario no encontrado";
    private final String PUBLICACION_NOT_FOUND = "Publicacion con id: %s no existe";

    @Autowired
    private PublicacionRepository publicacionRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/{id_usuario}")
    public void addPublicacion(@PathVariable Integer id_usuario, @RequestBody Publicacion p) {

        Usuario u = usuarioRepository.findById(id_usuario)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, String.format(USUARIO_NOT_FOUND,id_usuario)));

        p.setUsuario(u);
        u.getPublicaciones().add(p);

        publicacionRepository.save(p);
        usuarioRepository.save(u);
    }

    @GetMapping("")
    public List<Publicacion> getAll(){
        return publicacionRepository.findAll();
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

    @PutMapping("/{id}")
    public void updateById(@RequestBody Publicacion c, @PathVariable Integer id){
        Publicacion buscado = publicacionRepository.getOne(id);

        buscado.setTitulo(c.getTitulo());
        buscado.setDescripcion(c.getDescripcion());
        buscado.setFoto(c.getFoto());
        publicacionRepository.saveAndFlush(buscado);
    }

    @Scheduled(cron = "${scheduling.job.cron}")
    public void eliminaComentarios() {
        List<Publicacion> publicacionse = publicacionRepository.findAll();
        for(Publicacion p : publicacionse){

            p.getComentarios().removeIf(x -> (x.getFecha().plusMinutes(1).isBefore(LocalDateTime.now())));
            publicacionRepository.saveAndFlush(p);
        }
    }

    @GetMapping("/Native")
    public List<ComentariosPorPublicacion> getComentariosPorPublicacion(){
        return publicacionRepository.getComentariosPorPublicacion();
    }

}
