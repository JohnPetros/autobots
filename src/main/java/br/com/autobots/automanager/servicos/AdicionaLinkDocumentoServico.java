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
  public List<Documento> adicionarLink(List<Documento> documentos) {
    for (Documento documento : documentos) {
      long id = documento.getId();
      Link linkProprio = WebMvcLinkBuilder
          .linkTo(WebMvcLinkBuilder.methodOn(DocumentoControlador.class).obterDocumento(id))
          .withSelfRel();
      documento.add(linkProprio);
    }
    return documentos;
  }

  @Override
  public Documento adicionarLink(Documento documento) {
    Link linkProprio = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(DocumentoControlador.class)
            .obterDocumentos())
        .withRel("documentos");
    documento.add(linkProprio);
    return documento;
  }
}
