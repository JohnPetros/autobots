package br.com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import br.com.autobots.automanager.controladores.VendaControlador;
import br.com.autobots.automanager.entidades.Venda;

@Service
public class AdicionaLinkVendaServico implements AdicionaLinkServico<Venda> {

  @Override
  public void adicionarLink(List<Venda> vendas) {
    for (Venda venda : vendas) {
      adicionarLink(venda);
    }
  }

  @Override
  public void adicionarLink(Venda venda) {
    Link linkObter = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(VendaControlador.class)
            .obterVenda(venda.getId()))
        .withRel("obter venda");
    Link linkObterTodos = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(VendaControlador.class)
            .obterVendas())
        .withRel("obter todos os vendas");
    Link linkCadastrar = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(VendaControlador.class)
            .cadastrarVenda(null))
        .withRel("cadastrar venda");
    Link linkAtualizar = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(VendaControlador.class)
            .atualizarVenda(null))
        .withRel("atualizar venda");
    Link linkExcluir = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(VendaControlador.class)
            .excluirVenda(null))
        .withRel("excluir venda");
    venda.add(linkObter);
    venda.add(linkObterTodos);
    venda.add(linkCadastrar);
    venda.add(linkAtualizar);
    venda.add(linkExcluir);
  }
}
