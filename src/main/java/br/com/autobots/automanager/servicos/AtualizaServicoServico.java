package br.com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.automanager.entidades.Servico;

@Service
public class AtualizaServicoServico {
  @Autowired
  private VerificaStringNuloServico verificaStringServico;

  public void atualizar(Servico servico, Servico atualizacao) {
    if (atualizacao != null) {
      if (!verificaStringServico.verificar(atualizacao.getNome())) {
        servico.setNome(atualizacao.getNome());
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
