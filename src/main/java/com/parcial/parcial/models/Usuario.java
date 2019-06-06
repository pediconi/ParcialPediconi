package com.parcial.parcial.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String apellido;
    private String browser;
    @ToString.Exclude  // uso esto en la salida toString para evitar que me cargue toda la lista cada vez q quiera mostrar el equipo
    @OneToMany(fetch = FetchType.LAZY ,orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "usuario") // mapeo el lado propietario osea el equipo q es el q tiene los jugadores
    private List<Publicacion> publicaciones;
}
