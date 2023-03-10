package com.pildoralin.configservicio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigServicioApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServicioApplication.class, args);
	}

}
