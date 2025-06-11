package br.com.autobots.automanager.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.automanager.entidades.Telefone;
import br.com.autobots.automanager.excecoes.ConflitoExcecao;
import br.com.autobots.automanager.repositorios.TelefoneRepositorio;

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
