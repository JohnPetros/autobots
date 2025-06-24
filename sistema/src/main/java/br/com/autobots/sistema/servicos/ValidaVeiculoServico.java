package br.com.autobots.sistema.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.sistema.entidades.Veiculo;
import br.com.autobots.sistema.excecoes.ConflitoExcecao;
import br.com.autobots.sistema.repositorios.VeiculoRepositorio;

@Service
public class ValidaVeiculoServico {
  @Autowired
  private VeiculoRepositorio veiculoRepositorio;

  public void validar(Veiculo veiculo) {
    var veiculoExistente = veiculoRepositorio.findByPlaca(veiculo.getPlaca());
    if (veiculoExistente.isPresent()) {
      throw new ConflitoExcecao("Veículo já cadastrado com a placa: " + veiculo.getPlaca());
    }
  }
}