package com.parcial.parcial.repositories;

import com.parcial.parcial.models.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PublicacionRepository extends JpaRepository<Publicacion, Integer> {

    String NATIVE_QUERY = "Select p.titulo, u.nombre, count(c.id) as cantidadComentarios " +
            "from usuario u " +
            "inner join publicacion p " +
            "on u.id = p.usuario_id " +
            "join comentario c" +
            "on p.id = c.publicacion_id" +
            "group by p.titulo";

    @Query(value = NATIVE_QUERY , nativeQuery = true)
    List<ComentariosPorPublicacion> getComentariosPorPublicacion();

}

