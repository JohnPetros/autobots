package br.com.autobots.sistema.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.sistema.entidades.Telefone;
import br.com.autobots.sistema.excecoes.ConflitoExcecao;
import br.com.autobots.sistema.repositorios.TelefoneRepositorio;

@Service
public class ValidaTelefoneServico {
  @Autowired
  private TelefoneRepositorio telefoneRepositorio;

  public void validar(Telefone telefone) {
    if (telefone.getDdd() == null || telefone.getNumero() == null) {
      return;
    }
    var telefoneExistente = telefoneRepositorio.findByDddAndNumero(
        telefone.getDdd(),
        telefone.getNumero());
    if (telefoneExistente.isPresent()) {
      throw new ConflitoExcecao("Telefone já cadastrado");
    }
  }
}
