package br.com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.autobots.automanager.entidades.Venda;

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
      if (atualizacao.getFuncionario() != null) {
        venda.setFuncionario(atualizacao.getFuncionario());
      }
      if (atualizacao.getMercadorias() != null) {
        venda.setMercadorias(atualizacao.getMercadorias());
      }
      if (atualizacao.getServicos() != null) {
        venda.setServicos(atualizacao.getServicos());
      }
      if (atualizacao.getVeiculo() != null) {
        venda.setVeiculo(atualizacao.getVeiculo());
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
