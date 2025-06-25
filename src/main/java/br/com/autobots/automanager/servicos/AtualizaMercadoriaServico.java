package br.com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.automanager.entidades.Mercadoria;

@Service
public class AtualizaMercadoriaServico {
  public void atualizar(Mercadoria mercadoria, Mercadoria atualizacao) {
    if (atualizacao != null) {
      if (atualizacao.getNome() != null) {
        mercadoria.setNome(atualizacao.getNome());
      }
      if (atualizacao.getValor() > 0) {
        mercadoria.setValor(atualizacao.getValor());
      }
      if (atualizacao.getCadastro() != null) {
        mercadoria.setCadastro(atualizacao.getCadastro());
      }
      if (atualizacao.getFabricao() != null) {
        mercadoria.setFabricao(atualizacao.getFabricao());
      }
      if (atualizacao.getValidade() != null) {
        mercadoria.setValidade(atualizacao.getValidade());
      }
      if (atualizacao.getQuantidade() > 0) {
        mercadoria.setQuantidade(atualizacao.getQuantidade());
      }
      if (atualizacao.getDescricao() != null) {
        mercadoria.setDescricao(atualizacao.getDescricao());
      }
    }
  }

  public void atualizar(List<Mercadoria> mercadorias, List<Mercadoria> atualizacoes) {
    for (Mercadoria atualizacao : atualizacoes) {
      for (Mercadoria mercadoria : mercadorias) {
        if (atualizacao.getId() != null) {
          if (atualizacao.getId() == mercadoria.getId()) {
            atualizar(mercadoria, atualizacao);
          }
        }
      }
    }
  }
}
