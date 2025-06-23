package br.com.autobots.vendas.apis;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import br.com.autobots.vendas.entidades.Usuario;

@FeignClient(name = "usuarios", url = "http://localhost:8082")
public interface UsuariosApi {
  @GetMapping("/usuario/email/{email}")
  public Usuario obterUsuarioPorEmail(@RequestHeader("Authorization") String authorization, @PathVariable String email);

  @GetMapping("/usuario/{id}")
  public Usuario obterUsuarioPorId(@RequestHeader("Authorization") String authorization, @PathVariable Long id);
}
