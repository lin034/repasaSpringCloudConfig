package com.pildoralin.usuarioservicio.feignclient;

import com.pildoralin.usuarioservicio.modelo.Moto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name="moto-servicio")
@RequestMapping("/motos")
public interface MotoFeignClient {

    @PostMapping
    public Moto guardaMoto(@RequestBody Moto moto);
}
