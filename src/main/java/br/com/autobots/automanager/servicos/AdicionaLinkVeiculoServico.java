package br.com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import br.com.autobots.automanager.controladores.VeiculoControlador;
import br.com.autobots.automanager.entidades.Veiculo;

@Service
public class AdicionaLinkVeiculoServico implements AdicionaLinkServico<Veiculo> {

  @Override
  public void adicionarLink(List<Veiculo> veiculos) {
    for (Veiculo veiculo : veiculos) {
      adicionarLink(veiculo);
    }
  }

  @Override
  public void adicionarLink(Veiculo veiculo) {
    Link linkObter = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(VeiculoControlador.class)
            .obterVeiculo(veiculo.getId()))
        .withRel("obter veiculo");
    Link linkObterTodos = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(VeiculoControlador.class)
            .obterVeiculos())
        .withRel("obter todos os veiculos");
    Link linkCadastrar = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(VeiculoControlador.class)
            .cadastrarVeiculo(null))
        .withRel("cadastrar veiculo");
    Link linkAtualizar = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(VeiculoControlador.class)
            .atualizarVeiculo(null))
        .withRel("atualizar veiculo");
    Link linkExcluir = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(VeiculoControlador.class)
            .excluirVeiculo(null))
        .withRel("excluir veiculo");
    veiculo.add(linkObter);
    veiculo.add(linkObterTodos);
    veiculo.add(linkCadastrar);
    veiculo.add(linkAtualizar);
    veiculo.add(linkExcluir);
  }
}
