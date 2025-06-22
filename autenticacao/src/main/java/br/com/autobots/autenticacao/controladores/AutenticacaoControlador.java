package br.com.autobots.autenticacao.controladores;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.autobots.autenticacao.entidades.Credencial;

import lombok.Data;
import lombok.AllArgsConstructor;

import br.com.autobots.autenticacao.servicos.LoginServico;
import br.com.autobots.autenticacao.servicos.RegistraCredencialServico;
import br.com.autobots.autenticacao.servicos.ValidaCredencialServico;

@RestController
@RequestMapping
public class AutenticacaoControlador {
  @Autowired
  private LoginServico loginServico;

  @Autowired
  private RegistraCredencialServico registraCredencialServico;

  @Autowired
  private ValidaCredencialServico validaCredencialServico;

  @Data
  @AllArgsConstructor
  public static class Jwt {
    private String token;
  }

  @PostMapping("/autenticacao/login")
  public ResponseEntity<Jwt> login(@RequestBody Credencial credencial) {
    var token = loginServico.login(credencial);
    var jwt = new Jwt(token);
    return ResponseEntity.ok(jwt);
  }

  @PostMapping("/autenticacao/registrar")
  public ResponseEntity<Jwt> registrar(@RequestBody Credencial credencial) {
    validaCredencialServico.validar(credencial);
    var token = registraCredencialServico.registrar(credencial);
    var jwt = new Jwt(token);
    return ResponseEntity.ok(jwt);
  }
}
