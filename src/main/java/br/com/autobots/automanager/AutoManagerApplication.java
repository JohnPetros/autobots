package br.com.autobots.automanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableWebMvc
@OpenAPIDefinition(info = @Info(title = "Autobots API", version = "1.0", description = "API responsável"))
public class AutoManagerApplication {
	public static void main(String[] args) {
		SpringApplication.run(AutoManagerApplication.class, args);
	}
}
