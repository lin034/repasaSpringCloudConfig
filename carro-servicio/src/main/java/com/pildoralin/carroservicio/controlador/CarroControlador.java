package com.pildoralin.carroservicio.controlador;

import com.pildoralin.carroservicio.entidad.Carro;
import com.pildoralin.carroservicio.service.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/carros")
public class CarroControlador {

    @Autowired
    CarroService carroService;

    @GetMapping
    public ResponseEntity<List<Carro>> dameCarros(){

        List<Carro> carros = carroService.dameCarros();

        if(carros.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(carros);
    }

    @GetMapping("/{carroId}")
    public ResponseEntity<Carro> dameCarro(@PathVariable(name="carroId") int carroId){

        Carro carro = carroService.dameCarro(carroId);

        return ResponseEntity.ok(carro);
    }



    @PostMapping
    public ResponseEntity<Carro> guardaCarro(@RequestBody Carro carro){

        Carro carro1 = carroService.guardaCarro(carro);

        return new ResponseEntity<>(carro1, HttpStatus.CREATED);
        //return new ResponseEntity<>(carro1, HttpStatus.CREATED);

    }

    @GetMapping("/usuarios/{usuarioId}")
    public ResponseEntity<List<Carro>> dameCarrosPorUsuarioId(@PathVariable(name = "usuarioId") int usuarioId){



        List<Carro> carros = carroService.dameCarrosPorUsuarioId(usuarioId);

        /*if(carros.isEmpty()){
            return  ResponseEntity.noContent().build();
        }*/

        return ResponseEntity.ok(carros);
    }
}
