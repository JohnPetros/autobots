package br.com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.autobots.automanager.entidades.Mercadoria;

public class AtualizaMercadoriaServico {
  @Autowired
  private VerificaStringNuloServico verificaStringServico;

  @Autowired
  private VerificaDoubleNuloServico verificaDoubleServico;

  public void atualizar(Mercadoria mercadoria, Mercadoria atualizacao) {
    if (atualizacao != null) {
      if (!verificaStringServico.verificar(atualizacao.getNome())) {
        mercadoria.setNome(atualizacao.getNome());
      }
      if (!verificaDoubleServico.verificar(atualizacao.getValor())) {
        mercadoria.setValor(atualizacao.getValor());
      }
      if (!verificaStringServico.verificar(atualizacao.getDescricao())) {
        mercadoria.setDescricao(atualizacao.getDescricao());
      }
      if (!verificaStringServico.verificar(atualizacao.getDescricao())) {
        mercadoria.setDescricao(atualizacao.getDescricao());
      }
      if (!verificaStringServico.verificar(atualizacao.getDescricao())) {
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
