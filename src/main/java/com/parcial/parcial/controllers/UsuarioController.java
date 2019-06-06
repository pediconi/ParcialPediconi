package com.parcial.parcial.controllers;

import com.parcial.parcial.models.Usuario;
import com.parcial.parcial.repositories.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Component
@RequestMapping("/usuario")  // le indico que es una controladora osea cuando ponga localhost/persona viene a esta clase
@RestController
public class UsuarioController {

    private static final String PERSON_NOT_FOUND = "No existe el usuario con id: %s";

    @Autowired
    private UsuarioRepository usuarioRepository;   // usa el usuarioRepository q encuentre en el contexto

    @PostMapping("")
    public void addUsuario(@RequestBody Usuario u, @RequestHeader Map<String, String> headers) {   //convierte el JSON entrante a un objeto Candidato

        /*headers.forEach((key, value) -> {
            System.out.println(key +" : "+ value);           // muestro el map con todos los headers
        });*/

        //System.out.println(headers.get("user-agent"));   // obtengo el valor para la clave : "user-agent"

        u.setBrowser(headers.get("user-agent"));  // le seteo el navegador al candidato que agrego
        usuarioRepository. save(u);

    }

    @GetMapping("")
    public List<Usuario> getAll(){
        return usuarioRepository.findAll();   //retorno la lista
    }

    @GetMapping("/{id}")
    public Usuario getUsuarioById(@PathVariable Integer id){
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, String.format(PERSON_NOT_FOUND,id)));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id){
        usuarioRepository.deleteById(id);
    }




}
