package br.com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import br.com.autobots.automanager.controladores.MercadoriaControlador;
import br.com.autobots.automanager.entidades.Mercadoria;

@Service
public class AdicionaLinkMercadoriaServico implements AdicionaLinkServico<Mercadoria> {
  @Override
  public void adicionarLink(List<Mercadoria> mercadorias, Long empresaId) {
    for (Mercadoria mercadoria : mercadorias) {
      adicionarLink(mercadoria, empresaId);
    }
  }

  @Override
  public void adicionarLink(Mercadoria mercadoria, Long empresaId) {
    Link linkObter = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(MercadoriaControlador.class)
            .obterMercadoria(mercadoria.getId(), empresaId))
        .withRel("obter mercadoria");
    Link linkObterTodos = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(MercadoriaControlador.class)
            .obterMercadorias(empresaId))
        .withRel("obter todos os mercadorias");
    Link linkCadastrar = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(MercadoriaControlador.class)
            .cadastrarMercadoria(null, empresaId))
        .withRel("cadastrar mercadoria");
    Link linkAtualizar = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(MercadoriaControlador.class)
            .atualizarMercadoria(null, empresaId))
        .withRel("atualizar mercadoria");
    Link linkExcluir = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(MercadoriaControlador.class)
            .excluirMercadoria(null, empresaId))
        .withRel("excluir mercadoria");
    mercadoria.add(linkObter);
    mercadoria.add(linkObterTodos);
    mercadoria.add(linkCadastrar);
    mercadoria.add(linkAtualizar);
    mercadoria.add(linkExcluir);
  }
}
