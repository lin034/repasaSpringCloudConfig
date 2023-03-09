package com.pildoralin.motoservicio.service;

import com.pildoralin.motoservicio.entidad.Moto;
import com.pildoralin.motoservicio.repositorio.MotoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotoService {

    @Autowired
    MotoRepositorio motoRepositorio;


    public List<Moto> dameMotos() {

        return motoRepositorio.findAll();
    }

    public Moto dameMoto(int motoId) {

        return  motoRepositorio.findById(motoId).orElse(null);
    }

    public Moto guardaMoto(Moto moto) {

        return motoRepositorio.save(moto);
    }

    public List<Moto> dameMotosPorUsuarioId(int usuarioId) {

        return motoRepositorio.findByUsuarioId(usuarioId);
    }
}
