package com.pildoralin.carroservicio.repositorio;


import com.pildoralin.carroservicio.entidad.Carro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarroRepositorio extends JpaRepository<Carro,Integer> {

    public List<Carro> findByUsuarioId(int usuarioId);
}
