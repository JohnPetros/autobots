package br.com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import br.com.autobots.automanager.controladores.EnderecoControlador;
import br.com.autobots.automanager.entidades.Endereco;

@Service
public class AdicionaLinkEnderecoServico implements AdicionaLinkServico<Endereco> {

  @Override
  public void adicionarLink(List<Endereco> enderecos) {
    for (Endereco endereco : enderecos) {
      long id = endereco.getId();
      Link linkProprio = WebMvcLinkBuilder
          .linkTo(WebMvcLinkBuilder.methodOn(EnderecoControlador.class).obterEndereco(id))
          .withSelfRel();
      endereco.add(linkProprio);
    }
  }

  @Override
  public void adicionarLink(Endereco endereco) {
    Link linkProprio = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(EnderecoControlador.class)
            .obterEnderecos())
        .withRel("enderecos");
    endereco.add(linkProprio);
  }

}
