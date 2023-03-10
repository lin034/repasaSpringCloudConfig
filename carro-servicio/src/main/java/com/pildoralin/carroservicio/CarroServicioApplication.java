package com.pildoralin.carroservicio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CarroServicioApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarroServicioApplication.class, args);
	}

}
