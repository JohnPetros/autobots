package br.com.autobots.vendas.servicos;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.autobots.vendas.entidades.Venda;

@Service
public class AtualizaVendaServico {

  public void atualizar(Venda venda, Venda atualizacao) {
    if (atualizacao != null) {
      if (atualizacao.getIdentificacao() != null) {
        venda.setIdentificacao(atualizacao.getIdentificacao());
      }
      if (atualizacao.getCadastro() != null) {
        venda.setCadastro(atualizacao.getCadastro());
      }
      if (atualizacao.getCliente() != null) {
        venda.setCliente(atualizacao.getCliente());
      }
      if (atualizacao.getVendedor() != null) {
        venda.setVendedor(atualizacao.getVendedor());
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
