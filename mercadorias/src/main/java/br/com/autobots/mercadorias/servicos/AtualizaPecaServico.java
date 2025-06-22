package br.com.autobots.mercadorias.servicos;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.autobots.mercadorias.entidades.Peca;

@Service
public class AtualizaPecaServico {
  public void atualizar(Peca peca, Peca atualizacao) {
    if (atualizacao != null) {
      if (atualizacao.getNome() != null) {
        peca.setNome(atualizacao.getNome());
      }
      if (atualizacao.getValor() != 0) {
        peca.setValor(atualizacao.getValor());
      }
      if (atualizacao.getCadastro() != null) {
        peca.setCadastro(atualizacao.getCadastro());
      }
      if (atualizacao.getEmpresaId() != null) {
        peca.setEmpresaId(atualizacao.getEmpresaId());
      }
      if (atualizacao.getDescricao() != null) {
        peca.setDescricao(atualizacao.getDescricao());
      }
      if (atualizacao.getFabricao() != null) {
        peca.setFabricao(atualizacao.getFabricao());
      }
      if (atualizacao.getValidade() != null) {
        peca.setValidade(atualizacao.getValidade());
      }
      if (atualizacao.getQuantidade() >= 0) {
        peca.setQuantidade(atualizacao.getQuantidade());
      }
      if (atualizacao.getDescricao() != null) {
        peca.setDescricao(atualizacao.getDescricao());
      }
    }
  }

  public void atualizar(List<Peca> pecas, List<Peca> atualizacoes) {
    for (Peca atualizacao : atualizacoes) {
      for (Peca peca : pecas) {
        if (atualizacao.getId() != null) {
          if (atualizacao.getId() == peca.getId()) {
            atualizar(peca, atualizacao);
          }
        }
      }
    }
  }
}
