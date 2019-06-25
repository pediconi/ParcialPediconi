package com.parcial.parcial.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String descripcion;
    @JsonFormat(pattern="dd-MMM-yyyy")
    private LocalDateTime fecha;


    @JsonIgnore     // cuando quiera construir un json de este objeto, no tendra en cuenta el atributo candidato
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publicacion_id")   // foreign key indicando name: tablaReferenciada (el nombre que tendra el campo en ESTA tabla) + "_id"
    private Publicacion publicacion;                                        // y el campo referenciado de la otra tabla (referencedColumnName)

    @PrePersist // se ejecuta antes del save
    public void checkFecha(){
        if(this.fecha == null){
            this.fecha = LocalDateTime.now();

        }
    }
}
