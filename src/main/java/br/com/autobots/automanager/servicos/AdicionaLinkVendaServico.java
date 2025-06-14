package br.com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import br.com.autobots.automanager.controladores.VendaControlador;
import br.com.autobots.automanager.entidades.Venda;

@Service
public class AdicionaLinkVendaServico implements AdicionaLinkServico<Venda> {

  @Autowired
  private AdicionaLinkUsuarioServico adicionaLinkUsuarioServico;

  @Autowired
  private AdicionaLinkMercadoriaServico adicionaLinkMercadoriaServico;

  @Autowired
  private AdicionaLinkServicoServico adicionaLinkServicoServico;

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
            .obterVenda(venda.getId(), empresaId))
        .withRel("obter venda");
    Link linkObterTodos = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(VendaControlador.class)
            .obterVendas(empresaId))
        .withRel("obter todos os vendas");
    Link linkCadastrar = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(VendaControlador.class)
            .cadastrarVenda(null, empresaId))
        .withRel("cadastrar venda");
    Link linkAtualizar = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(VendaControlador.class)
            .atualizarVenda(null, empresaId))
        .withRel("atualizar venda");
    Link linkExcluir = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(VendaControlador.class)
            .excluirVenda(null, empresaId))
        .withRel("excluir venda");
    adicionaLinkUsuarioServico.adicionarLink(venda.getCliente(), empresaId);
    adicionaLinkUsuarioServico.adicionarLink(venda.getVendedor(), empresaId);
    if (!venda.getMercadorias().isEmpty()) {
      adicionaLinkMercadoriaServico.adicionarLink(venda.getMercadorias(), empresaId);
    }
    if (!venda.getServicos().isEmpty()) {
      adicionaLinkServicoServico.adicionarLink(venda.getServicos(), empresaId);
    }
    venda.add(linkObter);
    venda.add(linkObterTodos);
    venda.add(linkCadastrar);
    venda.add(linkAtualizar);
    venda.add(linkExcluir);
  }
}
