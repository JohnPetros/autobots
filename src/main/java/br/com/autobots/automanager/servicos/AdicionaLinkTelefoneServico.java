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
  public void adicionarLink(List<Telefone> telefones, Long empresaId) {
    for (Telefone telefone : telefones) {
      adicionarLink(telefone, empresaId);
    }
  }

  @Override
  public void adicionarLink(Telefone telefone, Long empresaId) {
    Link linkObter = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(TelefoneControlador.class)
            .obterTelefone(telefone.getId(), empresaId))
        .withRel("obter telefone");
    Link linkObterTodos = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(TelefoneControlador.class)
            .obterTelefones(empresaId))
        .withRel("obter todos os telefones");
    Link linkCadastrar = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(TelefoneControlador.class)
            .cadastrarTelefone(null, empresaId))
        .withRel("cadastrar telefone");
    Link linkAtualizar = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(TelefoneControlador.class)
            .atualizarTelefone(null, empresaId))
        .withRel("atualizar telefone");
    Link linkExcluir = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(TelefoneControlador.class)
            .excluirTelefone(null, empresaId))
        .withRel("excluir telefone");
    telefone.add(linkObter);
    telefone.add(linkObterTodos);
    telefone.add(linkCadastrar);
    telefone.add(linkAtualizar);
    telefone.add(linkExcluir);
  }
}
