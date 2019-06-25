package com.parcial.parcial.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Publicacion {

    //private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titulo;
    private String descripcion;
    private String foto;
    @JsonFormat(pattern="dd-MMM-yyyy")
    private LocalDateTime fechaPublicacion;
    private Integer likes;

    @JsonIgnore
    // ignoro al equipo en la serializacion asi no lo tengo q cargar en postman , si no que lo busco del repo
    @ManyToOne(fetch = FetchType.LAZY)   // lazy trae solo el proxy eager trae tode
    @JoinColumn(name = "usuario_id")    // foreign key tabla_ id referenciado
    private Usuario usuario;

    @ToString.Exclude  // uso esto en la salida toString para evitar que me cargue toda la lista cada vez q quiera mostrar el equipo
    @OneToMany(fetch = FetchType.EAGER ,orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "publicacion") // mapeo el lado propietario osea el equipo q es el q tiene los jugadores
    private List<Comentario> comentarios;

    @PrePersist // se ejecuta antes del save
    public void checkFecha(){
        if(this.fechaPublicacion == null){
            this.fechaPublicacion = LocalDateTime.now();
        }
    }

}
