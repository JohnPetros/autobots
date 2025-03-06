package br.com.autobots.server.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.server.entidades.Telefone;

@Service
public class AtualizaTelefoneServico {
  @Autowired
  private VerificaStringNuloServico verificaStringServico;

  public void atualizar(Telefone telefone, Telefone atualizacao) {
    if (atualizacao != null) {
      if (!verificaStringServico.verificar(atualizacao.getDdd())) {
        telefone.setDdd(atualizacao.getDdd());
      }
      if (!verificaStringServico.verificar(atualizacao.getNumero())) {
        telefone.setNumero(atualizacao.getNumero());
      }
    }
  }

  public void atualizar(List<Telefone> telefones, List<Telefone> atualizacoes) {
    for (Telefone atualizacao : atualizacoes) {
      for (Telefone telefone : telefones) {
        if (atualizacao.getId() != null) {
          if (atualizacao.getId() == telefone.getId()) {
            atualizar(telefone, atualizacao);
          }
        }
      }
    }
  }
}