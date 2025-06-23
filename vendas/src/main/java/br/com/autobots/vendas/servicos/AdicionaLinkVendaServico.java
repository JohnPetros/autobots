package br.com.autobots.vendas.servicos;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import br.com.autobots.vendas.controladores.VendaControlador;
import br.com.autobots.vendas.entidades.Venda;

@Service
public class AdicionaLinkVendaServico implements AdicionaLinkServico<Venda> {
  @Override
  public void adicionarLink(List<Venda> vendas, Long empresaId) {
    for (Venda venda : vendas) {
      adicionarLink(venda, empresaId);
    }
  }

  @Override
  public void adicionarLink(Venda venda, Long empresaId) {
    Link linkObter = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(VendaControlador.class)
            .obterVenda(venda.getId(), empresaId, null))
        .withRel("obter venda");
    Link linkObterTodos = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(VendaControlador.class)
            .obterVendas(empresaId, null, null, null))
        .withRel("obter todos os vendas");
    Link linkCadastrar = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(VendaControlador.class)
            .cadastrarVenda(null, empresaId, null))
        .withRel("cadastrar venda");
    Link linkAtualizar = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(VendaControlador.class)
            .atualizarVenda(null, empresaId, null))
        .withRel("atualizar venda");
    Link linkExcluir = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(VendaControlador.class)
            .excluirVenda(null, empresaId))
        .withRel("excluir venda");
    venda.add(linkObter);
    venda.add(linkObterTodos);
    venda.add(linkCadastrar);
    venda.add(linkAtualizar);
    venda.add(linkExcluir);
  }
}
