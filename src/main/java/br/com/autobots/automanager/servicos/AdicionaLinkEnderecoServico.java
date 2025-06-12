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
  public void adicionarLink(List<Endereco> enderecos, Long empresaId) {
    for (Endereco endereco : enderecos) {
      adicionarLink(endereco, empresaId);
    }
  }

  @Override
  public void adicionarLink(Endereco endereco, Long empresaId) {
    Link linkObter = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(EnderecoControlador.class)
            .obterEndereco(endereco.getId()))
        .withRel("obter endereco");
    Link linkObterTodos = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(EnderecoControlador.class)
            .obterEnderecos())
        .withRel("obter todos os enderecos");
    Link linkCadastrar = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(EnderecoControlador.class)
            .cadastrarEndereco(null))
        .withRel("cadastrar endereco");
    Link linkAtualizar = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(EnderecoControlador.class)
            .atualizarEndereco(null))
        .withRel("atualizar endereco");
    Link linkExcluir = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(EnderecoControlador.class)
            .excluirEndereco(null))
        .withRel("excluir endereco");
    endereco.add(linkObter);
    endereco.add(linkObterTodos);
    endereco.add(linkCadastrar);
    endereco.add(linkAtualizar);
    endereco.add(linkExcluir);
  }
}
