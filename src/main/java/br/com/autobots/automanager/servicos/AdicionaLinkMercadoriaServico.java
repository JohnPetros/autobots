package br.com.autobots.automanager.servicos;

import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import br.com.autobots.automanager.controladores.ServicoControlador;
import br.com.autobots.automanager.entidades.Mercadoria;
import br.com.autobots.automanager.entidades.Servico;

@Service
public class AdicionaLinkMercadoriaServico implements AdicionaLinkServico<Servico> {

  @Override
  public void adicionarLink(List<Mercadoria> mercadorias) {
    for (Mercadoria mercadoria : mercadorias) {
      long id = mercadoria.getId();
      Link linkProprio = WebMvcLinkBuilder
          .linkTo(WebMvcLinkBuilder.methodOn(MercadoriaControlador.class).obterMercadoria(id))
          .withSelfRel();
      mercadoria.add(linkProprio);
    }
  }

  @Override
  public void adicionarLink(Mercadoria mercadoria) {
    Link linkProprio = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(MercadoriaControlador.class)
            .obterMercadorias())
        .withRel("mercadorias");
    mercadoria.add(linkProprio);
  }

  @Override
  public void adicionarLink(Servico objeto) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'adicionarLink'");
  }

}
