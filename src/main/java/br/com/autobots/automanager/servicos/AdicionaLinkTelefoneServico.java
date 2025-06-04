package br.com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import br.com.autobots.automanager.controladores.TelefoneControlador;
import br.com.autobots.automanager.entidades.Telefone;

@Service
public class AdicionaLinkTelefoneServico implements AdicionaLinkServico<Telefone> {

  @Override
  public List<Telefone> adicionarLink(List<Telefone> telefones) {
    for (Telefone telefone : telefones) {
      long id = telefone.getId();
      Link linkProprio = WebMvcLinkBuilder
          .linkTo(WebMvcLinkBuilder.methodOn(TelefoneControlador.class).obterTelefone(id))
          .withSelfRel();
      telefone.add(linkProprio);
    }
    return telefones;
  }

  @Override
  public Telefone adicionarLink(Telefone telefone) {
    Link linkProprio = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(TelefoneControlador.class)
            .obterTelefones())
        .withRel("telefones");
    telefone.add(linkProprio);
    return telefone;
  }

}
