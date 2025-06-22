package br.com.autobots.usuarios.apis;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.Data;

@FeignClient(name = "autenticacao", url = "http://localhost:8081")
public interface AutenticacaoApi {

  @Data
  public static class JwtResponse {
    private String token;
  }

  @PostMapping("/registrar")
  public JwtResponse registrar(String nomeUsuario, String senha);
}
