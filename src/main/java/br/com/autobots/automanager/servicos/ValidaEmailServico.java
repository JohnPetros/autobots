package br.com.autobots.automanager.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.automanager.entidades.Email;
import br.com.autobots.automanager.excecoes.ConflitoExcecao;
import br.com.autobots.automanager.repositorios.EmailRepositorio;

@Service
public class ValidaEmailServico {
  @Autowired
  private EmailRepositorio emailRepositorio;

  public void validar(Email email) {
    if (email.getEndereco() == null) {
      return;
    }

    var emailExistente = emailRepositorio.findByEndereco(email.getEndereco());
    if (emailExistente.isPresent()) {
      throw new ConflitoExcecao("E-mail já cadastrado");
    }
  }
}
