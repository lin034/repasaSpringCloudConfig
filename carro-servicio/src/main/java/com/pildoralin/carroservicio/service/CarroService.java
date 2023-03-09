package com.pildoralin.carroservicio.service;

import com.pildoralin.carroservicio.entidad.Carro;
import com.pildoralin.carroservicio.repositorio.CarroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarroService {

    @Autowired
    CarroRepositorio carroRepositorio;


    public List<Carro> dameCarros() {

        return carroRepositorio.findAll();
    }

    public Carro dameCarro(int carroId){

        return carroRepositorio.findById(carroId).orElse(null);
    }

    public Carro guardaCarro(Carro carro) {

        return carroRepositorio.save(carro);
    }


    public List<Carro> dameCarrosPorUsuarioId(int usuarioId) {

        return carroRepositorio.findByUsuarioId(usuarioId);
    }
}
