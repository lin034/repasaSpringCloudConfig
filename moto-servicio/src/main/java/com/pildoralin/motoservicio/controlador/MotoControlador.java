package com.pildoralin.motoservicio.controlador;

import com.pildoralin.motoservicio.entidad.Moto;
import com.pildoralin.motoservicio.service.MotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/motos")
public class MotoControlador {

    @Autowired
    MotoService motoService;

    @GetMapping
    public ResponseEntity<List<Moto>> dameMotos(){

        List<Moto> motos = motoService.dameMotos();

        if(motos.isEmpty()){

            ResponseEntity.noContent().build();
        }

        return new ResponseEntity<>(motos, HttpStatus.CREATED);
    }

    @GetMapping("/{motoId}")
    public ResponseEntity<Moto> dameMoto(@PathVariable(name = "motoId") int motoId){

        Moto moto = motoService.dameMoto(motoId);

        return ResponseEntity.ok(moto);
    }

    @PostMapping
    public ResponseEntity<Moto> guardaMoto(@RequestBody Moto moto){

        Moto moto1 = motoService.guardaMoto(moto);

        return  new ResponseEntity<>(moto1, HttpStatus.ACCEPTED);
    }

    @GetMapping("/usuarios/{usuarioId}")
    public ResponseEntity<List<Moto>> dameMotosPorUsuarioId(@PathVariable(name ="usuarioId") int usuarioId){

        List<Moto> motos = motoService.dameMotosPorUsuarioId(usuarioId);

        /*if(motos.isEmpty()){

            return new ResponseEntity("el usuario no cuenta con motos por ahora", HttpStatus.NO_CONTENT);
        }*/

        return ResponseEntity.ok(motos);
    }
}
