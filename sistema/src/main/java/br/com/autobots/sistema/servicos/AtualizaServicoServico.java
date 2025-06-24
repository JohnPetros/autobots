package br.com.autobots.sistema.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.sistema.entidades.Servico;

@Service
public class AtualizaServicoServico {
  @Autowired
  private VerificaStringNuloServico verificaStringServico;

  public void atualizar(Servico servico, Servico atualizacao) {
    if (atualizacao != null) {
      if (atualizacao.getValor() != null) {
        servico.setValor(atualizacao.getValor());
      }
      if (atualizacao.getValor() != null) {
        servico.setValor(atualizacao.getValor());
      }
      if (!verificaStringServico.verificar(atualizacao.getDescricao())) {
        servico.setDescricao(atualizacao.getDescricao());
      }
    }
  }

  public void atualizar(List<Servico> servicos, List<Servico> atualizacoes) {
    for (Servico atualizacao : atualizacoes) {
      for (Servico servico : servicos) {
        if (atualizacao.getId() != null) {
          if (atualizacao.getId() == servico.getId()) {
            atualizar(servico, atualizacao);
          }
        }
      }
    }
  }
}