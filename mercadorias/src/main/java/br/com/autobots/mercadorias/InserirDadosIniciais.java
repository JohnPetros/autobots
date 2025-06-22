package br.com.autobots.mercadorias;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.autobots.mercadorias.entidades.Peca;
import br.com.autobots.mercadorias.entidades.Servico;
import br.com.autobots.mercadorias.repositorios.PecaRepositorio;
import br.com.autobots.mercadorias.repositorios.ServicoRepositorio;

@Component
public class InserirDadosIniciais implements CommandLineRunner {
  @Autowired
  private PecaRepositorio pecaRepositorio;

  @Autowired
  private ServicoRepositorio servicoRepositorio;

  @Override
  public void run(String... args) throws Exception {
    var peca = inserirPecas();
    pecaRepositorio.saveAll(peca);

    var servico = inserirServicos();
    servicoRepositorio.saveAll(servico);
  }

  private List<Peca> inserirPecas() {
    var peca1 = new Peca();
    peca1.setNome("Peca 1");
    peca1.setDescricao("Descricao da Peca 1");
    peca1.setValor(100.0);
    peca1.setQuantidade(10);
    peca1.setCadastro(LocalDate.now().minusDays(1));
    peca1.setFabricao(LocalDate.now().minusDays(1));
    peca1.setValidade(LocalDate.now().plusDays(30));
    peca1.setEmpresaId(1L);

    return List.of(peca1);
  }

  private List<Servico> inserirServicos() {
    var servico1 = new Servico();
    servico1.setNome("Servico 1");
    servico1.setDescricao("Descricao do Servico 1");
    servico1.setValor(100.0);
    servico1.setEmpresaId(1L);

    return List.of(servico1);
  }
}
