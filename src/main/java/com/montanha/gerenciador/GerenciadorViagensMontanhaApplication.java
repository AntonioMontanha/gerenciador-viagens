package com.montanha.gerenciador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class GerenciadorViagensMontanhaApplication {

	public static void main(String[] args) {
		SpringApplication.run(GerenciadorViagensMontanhaApplication.class, args);
	}

}
