package br.com.autobots.automanager.servicos;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

import br.com.autobots.automanager.controladores.ServicoControlador;
import br.com.autobots.automanager.entidades.Servico;

@Service
public class AdicionaLinkServicoServico implements AdicionaLinkServico<Servico> {
  @Override
  public void adicionarLink(List<Servico> servicos) {
    for (Servico servico : servicos) {
      adicionarLink(servico);
    }
  }

  @Override
  public void adicionarLink(Servico servico) {
    var linkObter = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(ServicoControlador.class)
            .obterServico(servico.getId()))
        .withRel("obter servico");
    var linkObterTodos = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(ServicoControlador.class)
            .obterServicos())
        .withRel("obter todos os servicos");
    var linkCadastrar = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(ServicoControlador.class)
            .cadastrarServico(null))
        .withRel("cadastrar servico");
    var linkAtualizar = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(ServicoControlador.class)
            .atualizarServico(null))
        .withRel("atualizar servico");
    var linkExcluir = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(ServicoControlador.class)
            .excluirServico(null))
        .withRel("excluir servico");
    servico.add(linkObter);
    servico.add(linkObterTodos);
    servico.add(linkCadastrar);
    servico.add(linkAtualizar);
    servico.add(linkExcluir);
  }
}
