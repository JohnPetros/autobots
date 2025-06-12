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
  public void adicionarLink(List<Documento> documentos, Long empresaId) {
    for (Documento documento : documentos) {
      adicionarLink(documento, empresaId);
    }
  }

  @Override
  public void adicionarLink(Documento documento, Long empresaId) {
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
    Link linkAtualizar = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(DocumentoControlador.class)
            .atualizarDocumento(null))
        .withRel("atualizar documento");
    documento.add(linkObter);
    documento.add(linkObterTodos);
    documento.add(linkAtualizar);
  }
}
