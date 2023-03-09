package com.pildoralin.motoservicio.repositorio;

import com.pildoralin.motoservicio.entidad.Moto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotoRepositorio extends JpaRepository<Moto, Integer> {

    public List<Moto> findByUsuarioId(int usuarioId);
}
