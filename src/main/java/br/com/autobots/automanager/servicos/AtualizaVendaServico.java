package br.com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.automanager.entidades.Venda;

@Service
public class AtualizaVendaServico {
  @Autowired
  private VerificaStringNuloServico verificaStringServico;

  public void atualizar(Venda venda, Venda atualizacao) {
    if (atualizacao != null) {
      if (!verificaStringServico.verificar(atualizacao.getIdentificacao())) {
        venda.setIdentificacao(atualizacao.getIdentificacao());
      }
      if (!verificaStringServico.verificar(atualizacao.getCadastro().toString())) {
        venda.setCadastro(atualizacao.getCadastro());
      }

    }
  }

  public void atualizar(List<Venda> vendas, List<Venda> atualizacoes) {
    for (Venda atualizacao : atualizacoes) {
      for (Venda venda : vendas) {
        if (atualizacao.getId() != null) {
          if (atualizacao.getId() == venda.getId()) {
            atualizar(venda, atualizacao);
          }
        }
      }
    }
  }
}
