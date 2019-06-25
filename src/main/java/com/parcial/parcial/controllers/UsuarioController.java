package com.parcial.parcial.controllers;

import com.parcial.parcial.models.Usuario;
import com.parcial.parcial.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import java.util.List;
import java.util.Map;


@Component
@RequestMapping("/usuario")
@RestController
public class UsuarioController {

    private static final String PERSON_NOT_FOUND = "No existe el usuario con id: %s";

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("")
    public void addUsuario(@RequestBody Usuario u, @RequestHeader Map<String, String> headers) {

        /*headers.forEach((key, value) -> {
            System.out.println(key +" : "+ value);
        });*/

        u.setBrowser(headers.get("user-agent"));
        usuarioRepository. save(u);

    }

    @GetMapping("")
    public List<Usuario> getAll(){
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public Usuario getUsuarioById(@PathVariable Integer id){
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, String.format(PERSON_NOT_FOUND,id)));
    }

    @PutMapping("/{id}")
    public void updateById(@RequestBody Usuario c, @PathVariable Integer id){
        Usuario buscado = usuarioRepository.getOne(id);

        buscado.setNombre(c.getNombre());
        buscado.setApellido(c.getApellido());
        usuarioRepository.saveAndFlush(buscado);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id){
        usuarioRepository.deleteById(id);
    }
}
