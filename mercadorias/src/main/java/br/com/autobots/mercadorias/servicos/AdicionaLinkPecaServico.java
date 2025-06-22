package br.com.autobots.mercadorias.servicos;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import br.com.autobots.mercadorias.controladores.PecaControlador;
import br.com.autobots.mercadorias.entidades.Peca;

@Service
public class AdicionaLinkPecaServico implements AdicionaLinkServico<Peca> {

  @Override
  public void adicionarLink(List<Peca> pecas, Long empresaId) {
    for (Peca peca : pecas) {
      adicionarLink(peca, empresaId);
    }
  }

  @Override
  public void adicionarLink(Peca peca, Long empresaId) {
    Link linkObter = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(PecaControlador.class)
            .obterPeca(peca.getId(), empresaId))
        .withRel("obter peça");
    Link linkObterTodos = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(PecaControlador.class)
            .obterPecas(empresaId))
        .withRel("obter todas as peças");
    Link linkCadastrar = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(PecaControlador.class)
            .cadastrarPeca(null, empresaId))
        .withRel("cadastrar peça");
    Link linkAtualizar = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(PecaControlador.class)
            .atualizarPeca(null, empresaId))
        .withRel("atualizar peça");
    Link linkExcluir = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(PecaControlador.class)
            .excluirPeca(null, empresaId))
        .withRel("excluir peça");
    peca.add(linkObter);
    peca.add(linkObterTodos);
    peca.add(linkCadastrar);
    peca.add(linkAtualizar);
    peca.add(linkExcluir);
  }
}
