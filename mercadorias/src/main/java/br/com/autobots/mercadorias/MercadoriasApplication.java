package br.com.autobots.mercadorias;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MercadoriasApplication {
	public static void main(String[] args) {
		SpringApplication.run(MercadoriasApplication.class, args);
	}
}
