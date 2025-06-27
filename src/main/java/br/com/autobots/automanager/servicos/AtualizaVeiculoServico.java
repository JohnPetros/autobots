package br.com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.autobots.automanager.entidades.Veiculo;

@Service
public class AtualizaVeiculoServico {

  public void atualizar(Veiculo veiculo, Veiculo atualizacao) {
    if (atualizacao != null) {
      if (atualizacao.getTipo() != null) {
        veiculo.setTipo(atualizacao.getTipo());
      }
      if (atualizacao.getModelo() != null) {
        veiculo.setModelo(atualizacao.getModelo());
      }
      if (atualizacao.getPlaca() != null) {
        veiculo.setPlaca(atualizacao.getPlaca());
      }
    }
  }

  public void atualizar(List<Veiculo> veiculos, List<Veiculo> atualizacoes) {
    for (Veiculo atualizacao : atualizacoes) {
      for (Veiculo veiculo : veiculos) {
        if (atualizacao.getId() != null) {
          if (atualizacao.getId() == veiculo.getId()) {
            atualizar(veiculo, atualizacao);
          }
        }
      }
    }
  }
}