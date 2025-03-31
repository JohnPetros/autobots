package br.com.autobots.automanager.servicos;

import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import br.com.autobots.automanager.controladores.ServicoControlador;
import br.com.autobots.automanager.entidades.Servico;

@Service
public class AdicionaLinkServicoServico implements AdicionaLinkServico<Servico> {

  @Override
  public void adicionarLink(List<Servico> servicos) {
    for (Servico servico : servicos) {
      long id = servico.getId();
      Link linkProprio = WebMvcLinkBuilder
          .linkTo(WebMvcLinkBuilder.methodOn(ServicoControlador.class).obterServico(id))
          .withSelfRel();
      servico.add(linkProprio);
    }
  }

  @Override
  public void adicionarLink(Servico servico) {
    Link linkProprio = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(ServicoControlador.class)
            .obterServicos())
        .withRel("servicos");
    servico.add(linkProprio);
  }

}
