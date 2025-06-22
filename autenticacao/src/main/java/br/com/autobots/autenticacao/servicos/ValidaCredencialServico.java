package br.com.autobots.autenticacao.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.autenticacao.entidades.Credencial;
import br.com.autobots.autenticacao.excecoes.ConflitoExcecao;
import br.com.autobots.autenticacao.repositorios.CredencialRepositorio;

@Service
public class ValidaCredencialServico {
  @Autowired
  private CredencialRepositorio credencialRepositorio;

  public void validar(Credencial credencial) {
    if (credencial == null) {
      return;
    }

    var credencialExistente = credencialRepositorio
        .findByEmail(credencial.getEmail());

    if (credencialExistente.isPresent()) {
      throw new ConflitoExcecao("Credencial já cadastrada com esse e-mail");
    }
  }
}
