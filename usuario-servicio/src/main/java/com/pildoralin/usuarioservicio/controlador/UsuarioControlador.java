package com.pildoralin.usuarioservicio.controlador;

import com.pildoralin.usuarioservicio.entidad.Usuario;
import com.pildoralin.usuarioservicio.feignclient.CarroFeignClient;
import com.pildoralin.usuarioservicio.feignclient.MotoFeignClient;
import com.pildoralin.usuarioservicio.modelo.Carro;
import com.pildoralin.usuarioservicio.modelo.Moto;
import com.pildoralin.usuarioservicio.service.UsuarioService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControlador {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    CarroFeignClient carroFeignClient;

    @Autowired
    MotoFeignClient motoFeignClient;

    @GetMapping
    public ResponseEntity<List<Usuario>> dameUsuarios(){

        List<Usuario> usuarios = usuarioService.dameUsuarios();

        if(usuarios.isEmpty()){

            return new ResponseEntity("lista de usuarios vacia", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(usuarios, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<Usuario> dameUsuario(@PathVariable(name = "usuarioId") int usuarioId){

        Usuario usuario = usuarioService.dameUsuario(usuarioId);

        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<Usuario> guardaUsuario(@RequestBody Usuario usuario){

        Usuario usuario1 = usuarioService.guardaUsuario(usuario);

        return new ResponseEntity<>(usuario1, HttpStatus.CREATED);
    }

    @CircuitBreaker(name = "carrosCB", fallbackMethod = "fallBackDameCarros")
    @GetMapping("/carros/{usuarioId}")
    public ResponseEntity<List<Carro>> dameCarrosPorUsuarioId(@PathVariable(name="usuarioId") int usuarioId){

        Usuario usuario = usuarioService.dameUsuario(usuarioId);
        if(usuario == null){

            return new ResponseEntity("no existe ese usuario intente de nuevo", HttpStatus.NOT_FOUND);
        }

        List<Carro> carros = usuarioService.dameCarrosPorUsuarioId(usuarioId);

        if(carros.isEmpty()){

            return  new ResponseEntity("el usuario no tiene carros", HttpStatus.NOT_FOUND);
        }


        return ResponseEntity.ok(carros);
    }

    @CircuitBreaker(name = "motosCB", fallbackMethod = "fallBackDameMotos")
    @GetMapping("/motos/{usuarioId}")
    public ResponseEntity<List<Moto>> dameMotosPorUsuarioId(@PathVariable(name ="usuarioId") int usuarioId){

        Usuario usuario = usuarioService.dameUsuario(usuarioId);

        if(usuario == null){

            return new ResponseEntity("no existe ese usuario intente de nuevo", HttpStatus.NOT_FOUND);
        }

        List<Moto> motos = usuarioService.dameMotosPorUsuarioId(usuarioId);

        if(motos.isEmpty()){

            return  new ResponseEntity("el usuario no tiene motos", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(motos);
    }

    @CircuitBreaker(name = "carrosCB", fallbackMethod = "fallBackGuardaCarro")
    @PostMapping("/carros/{usuarioId}")
    public ResponseEntity<Carro> guardaCarro(@RequestBody Carro carro, @PathVariable(name="usuarioId") int usuarioId){

        Usuario usuario = usuarioService.dameUsuario(usuarioId);
        if(usuario == null){
            return new ResponseEntity("el usuario no existe intente de nuevo", HttpStatus.NOT_FOUND);
        }

        carro.setUsuarioId(usuarioId);

        Carro carro1 = carroFeignClient.guardaCarro(carro);

        return new ResponseEntity<>(carro1, HttpStatus.CREATED);
    }

    @CircuitBreaker(name = "motosCB", fallbackMethod = "fallBackGuardaMoto")
    @PostMapping("/motos/{usuarioId}")
    public ResponseEntity<Moto> guardaMoto(@RequestBody Moto moto, @PathVariable(name="usuarioId") int usuarioId){

        Usuario usuario = usuarioService.dameUsuario(usuarioId);

        if (usuario == null) {

            return new ResponseEntity("el usuario no existe intente de nuevo", HttpStatus.NOT_FOUND);
        }

        moto.setUsuarioId(usuarioId);

        Moto moto1 = motoFeignClient.guardaMoto(moto);

        return new ResponseEntity<>(moto1, HttpStatus.CREATED);
    }

    @CircuitBreaker(name = "todosCB", fallbackMethod = "fallBackDameVehiculos")
    @GetMapping("/todos/{usuarioId}")
    public ResponseEntity<Map<String, Object>> dameVehiculos(@PathVariable(name="usuarioId") int usuarioId){

        Usuario usuario = usuarioService.dameUsuario(usuarioId);

        if(usuario == null){
            return new ResponseEntity("el usuario no existe intente de nuevo", HttpStatus.NOT_FOUND);
        }

        Map<String, Object> vehiculos = usuarioService.dameVehiculosUsuario(usuarioId);

        return ResponseEntity.ok(vehiculos);
    }

    private ResponseEntity<List<Carro>> dameCarrosPorUsuarioId(@PathVariable(name="usuarioId") int usuarioId, RuntimeException exception){

        return new ResponseEntity("el microservicio dame carros por usuario id fuera de servicio intente mas tarde", HttpStatus.OK);
    }

    private ResponseEntity<List<Moto>> dameMotosPorUsuarioId(@PathVariable(name="usuarioId") int usuarioId, RuntimeException exception){

        return new ResponseEntity("el microservicio dame motos por usuario id fuera de servicio intente mas tarde", HttpStatus.OK);
    }

    private ResponseEntity<Carro> guardaCarro(@RequestBody Carro carro, @PathVariable(name="usuarioId") int usuarioId, RuntimeException exception){

        return new ResponseEntity("el microservicio guarda carro fuera de servicio intente mas tarde", HttpStatus.OK);
    }

    private ResponseEntity<Carro> guardaMoto(@RequestBody Moto moto, @PathVariable(name="usuarioId") int usuarioId, RuntimeException exception){

        return new ResponseEntity("el microservicio guarda moto fuera de servicio intente mas tarde", HttpStatus.OK);
    }

    private ResponseEntity<Map<String, Object>> dameVehiculos(@PathVariable(name="usuarioId") int usuarioId, RuntimeException exception){

        return new ResponseEntity("el microservicio dame vehiculos por usuario fuera de servivio intente mas tarde", HttpStatus.OK);
    }
}
