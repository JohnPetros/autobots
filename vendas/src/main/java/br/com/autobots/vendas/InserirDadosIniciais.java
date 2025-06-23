package br.com.autobots.vendas;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.autobots.vendas.entidades.Venda;
import br.com.autobots.vendas.repositorios.VendaRepositorio;

@Component
public class InserirDadosIniciais implements CommandLineRunner {

  @Autowired
  private VendaRepositorio vendaRepositorio;

  @Override
  public void run(String... args) throws Exception {
    var vendas = inserirVendas();
    vendaRepositorio.saveAll(vendas);
  }

  private List<Venda> inserirVendas() {
    var venda1 = new Venda();
    venda1.setIdentificacao("1234567890");
    venda1.setCadastro(LocalDate.of(2020, 1, 1));
    venda1.setPecasIds(List.of(1L));
    venda1.setServicosIds(List.of(1L));
    venda1.setClienteId(2L);
    venda1.setVendedorId(5L);
    venda1.setVeiculoId(1L);
    venda1.setEmpresaId(1L);

    var venda2 = new Venda();
    venda2.setIdentificacao("2234567890");
    venda2.setCadastro(LocalDate.of(2025, 1, 1));
    venda2.setPecasIds(List.of(1L));
    venda2.setClienteId(2L);
    venda2.setVendedorId(5L);
    venda2.setVeiculoId(1L);
    venda2.setEmpresaId(1L);

    return List.of(venda1, venda2);
  }
}
