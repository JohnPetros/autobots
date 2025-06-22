package br.com.autobots.veiculos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.autobots.veiculos.entidades.Veiculo;
import br.com.autobots.veiculos.enums.TipoVeiculo;
import br.com.autobots.veiculos.repositorios.VeiculoRepositorio;

@Component
public class InserirDadosIniciais implements CommandLineRunner {
  @Autowired
  private VeiculoRepositorio veiculoRepositorio;

  @Override
  public void run(String... args) throws Exception {
    var veiculo = inserirVeiculos();
    veiculoRepositorio.saveAll(veiculo);
  }

  private List<Veiculo> inserirVeiculos() {
    var veiculo1 = new Veiculo();
    veiculo1.setTipo(TipoVeiculo.HATCH);
    veiculo1.setModelo("Gol");
    veiculo1.setPlaca("ABC-1234");
    veiculo1.setProprietarioId(2L);
    veiculo1.setEmpresaId(1L);

    var veiculo2 = new Veiculo();
    veiculo2.setTipo(TipoVeiculo.HATCH);
    veiculo2.setModelo("Gol");
    veiculo2.setPlaca("ABC-1234");
    veiculo2.setProprietarioId(2L);
    veiculo2.setEmpresaId(1L);

    return List.of(veiculo1);
  }
}
