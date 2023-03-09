package com.pildoralin.usuarioservicio.feignclient;

import com.pildoralin.usuarioservicio.modelo.Carro;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name="carro-servicio", url ="http://localhost:8081")
@RequestMapping("/carros")
public interface CarroFeignClient {

    @PostMapping
    public Carro guardaCarro(@RequestBody Carro carro);


}
