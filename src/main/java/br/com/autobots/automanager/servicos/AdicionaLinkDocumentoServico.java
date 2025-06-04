package br.com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import br.com.autobots.automanager.controladores.DocumentoControlador;
import br.com.autobots.automanager.entidades.Documento;

@Service
public class AdicionaLinkDocumentoServico implements AdicionaLinkServico<Documento> {

  @Override
  public void adicionarLink(List<Documento> documentos) {
    for (Documento documento : documentos) {
      adicionarLink(documento);
    }
  }

  @Override
  public void adicionarLink(Documento documento) {
    Link linkObter = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(DocumentoControlador.class)
            .obterDocumento(documento.getId()))
        .withRel("obter documento");
    Link linkObterTodos = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(DocumentoControlador.class)
            .obterDocumentos())
        .withRel("obter todos os documentos");
    Link linkCadastrar = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(DocumentoControlador.class)
            .cadastrarDocumento(null))
        .withRel("cadastrar documento");
    Link linkAtualizar = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(DocumentoControlador.class)
            .atualizarDocumento(null))
        .withRel("atualizar documento");
    Link linkExcluir = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(DocumentoControlador.class)
            .excluirDocumento(null))
        .withRel("excluir documento");
    documento.add(linkObter);
    documento.add(linkObterTodos);
    documento.add(linkCadastrar);
    documento.add(linkAtualizar);
    documento.add(linkExcluir);
  }
}
