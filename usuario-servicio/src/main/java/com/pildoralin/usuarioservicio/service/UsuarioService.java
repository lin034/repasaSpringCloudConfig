package com.pildoralin.usuarioservicio.service;


import com.pildoralin.usuarioservicio.entidad.Usuario;

import com.pildoralin.usuarioservicio.modelo.Carro;
import com.pildoralin.usuarioservicio.modelo.Moto;
import com.pildoralin.usuarioservicio.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsuarioService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    public Usuario dameUsuario(int usuarioId) {

        return usuarioRepositorio.findById(usuarioId).orElse(null);
    }

    public List<Usuario> dameUsuarios() {

        return usuarioRepositorio.findAll();
    }

    public Usuario guardaUsuario(Usuario usuario) {

        return usuarioRepositorio.save(usuario);
    }


    public List<Carro> dameCarrosPorUsuarioId(int usuarioId){

        List<Carro> carros = restTemplate.getForObject("http://carro-servicio/carros/usuarios/" + usuarioId, List.class);
        //List<Carro> carros = restTemplate.getForObject("http://localhost:8081/carros/usuarios/" + usuarioId, List.class);

        return  carros;
    }

    public List<Moto> dameMotosPorUsuarioId(int usuarioId) {

        List<Moto> motos = restTemplate.getForObject("http://moto-servicio/motos/usuarios/" + usuarioId, List.class);
        //List<Moto> motos = restTemplate.getForObject("http://localhost:8082/motos/usuarios/" + usuarioId, List.class);

        return motos;
    }

    public Map<String, Object> dameVehiculosUsuario(int usuarioId) {

        Map<String, Object> vehiculos = new HashMap<>();

        Usuario usuario = usuarioRepositorio.findById(usuarioId).orElse(null);

        if(usuario == null){

            vehiculos.put("mensaje", "el usuario no eesta intente de nuevo");

            return vehiculos;
        }
        vehiculos.put("usuario:", usuario);

        List<Moto> motos = dameMotosPorUsuarioId(usuarioId);

        if(motos.isEmpty()){

            vehiculos.put("mensaje", "el usuario no cuenta con motos");
        }else {
            vehiculos.put("motos", motos);
        }
        List<Carro> carros = dameCarrosPorUsuarioId(usuarioId);

        if(carros.isEmpty()){

            vehiculos.put("mensaje", "el usuario no cuenta con carros");
        }else {

            vehiculos.put("carros", carros);
        }
        return vehiculos;
    }
}
