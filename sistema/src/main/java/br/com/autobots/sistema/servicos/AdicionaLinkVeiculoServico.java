package br.com.autobots.sistema.servicos;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import br.com.autobots.sistema.controladores.VeiculoControlador;
import br.com.autobots.sistema.entidades.Veiculo;
import br.com.autobots.sistema.enums.TipoVeiculo;

@Service
public class AdicionaLinkVeiculoServico implements AdicionaLinkServico<Veiculo> {

  @Override
  public void adicionarLink(List<Veiculo> veiculos, Long empresaId) {
    for (Veiculo veiculo : veiculos) {
      adicionarLink(veiculo, empresaId);
    }
  }

  @Override
  public void adicionarLink(Veiculo veiculo, Long empresaId) {
    Link linkObter = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(VeiculoControlador.class)
            .obterVeiculo(veiculo.getId(), empresaId))
        .withRel("obter veiculo");
    Link linkObterTodos = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(VeiculoControlador.class)
            .obterVeiculos(empresaId))
        .withRel("obter todos os veiculos");
    Link linkCadastrar = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(VeiculoControlador.class)
            .cadastrarVeiculo(null, empresaId))
        .withRel("cadastrar veiculo");
    Link linkAtualizar = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(VeiculoControlador.class)
            .atualizarVeiculo(null, empresaId))
        .withRel("atualizar veiculo");
    Link linkExcluir = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(VeiculoControlador.class)
            .excluirVeiculo(null, empresaId))
        .withRel("excluir veiculo");
    veiculo.add(linkObter);
    veiculo.add(linkObterTodos);
    veiculo.add(linkCadastrar);
    veiculo.add(linkAtualizar);
    veiculo.add(linkExcluir);
  }
}