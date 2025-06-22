package br.com.autobots.mercadorias.apis;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import br.com.autobots.mercadorias.entidades.Usuario;

@FeignClient(name = "autenticacao", url = "http://localhost:8082")
public interface UsuariosApi {
  @GetMapping("/usuario/email/{email}")
  public Usuario obterUsuarioPorEmail(@RequestHeader("Authorization") String authorization, @PathVariable String email);

  @GetMapping("/usuario/{id}")
  public Usuario obterUsuarioPorId(@RequestHeader("Authorization") String authorization, @PathVariable Long id);
}
