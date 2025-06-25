package br.com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.autobots.automanager.entidades.Telefone;

@Service
public class AtualizaTelefoneServico {

  public void atualizar(Telefone telefone, Telefone atualizacao) {
    if (atualizacao != null) {
      if (atualizacao.getDdd() != null) {
        telefone.setDdd(atualizacao.getDdd());
      }
      if (atualizacao.getNumero() != null) {
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