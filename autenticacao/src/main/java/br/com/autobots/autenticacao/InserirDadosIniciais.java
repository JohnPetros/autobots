package br.com.autobots.autenticacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.autobots.autenticacao.entidades.Credencial;
import br.com.autobots.autenticacao.repositorios.CredencialRepositorio;

@Component
public class InserirDadosIniciais implements CommandLineRunner {
  @Autowired
  private CredencialRepositorio credencialRepositorio;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) throws Exception {
    credencialRepositorio.deleteAll();
    var credencial = inserirCredencial();
    credencialRepositorio.save(credencial);
  }

  private Credencial inserirCredencial() {
    var credencial = new Credencial();
    var encryptedPassword = passwordEncoder.encode("123456");
    credencial.setEmail("admin@autobots.com");
    credencial.setSenha(encryptedPassword);
    credencial.setUsuarioId(1L);
    return credencial;
  }

}
