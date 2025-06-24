package br.com.autobots.server.servicos;

import org.springframework.stereotype.Service;

import br.com.autobots.server.dtos.DocumentoDto;
import br.com.autobots.server.entidades.Documento;

@Service
public class CadastraDocumentoServico {
  public Documento cadastrar(DocumentoDto documento) {
    var documentoEntity = new Documento();
    documentoEntity.setTipo(documento.getTipo());
    documentoEntity.setNumero(documento.getNumero());
    documentoEntity.setClienteId(documento.getClienteId());
    return documentoEntity;
  }
}
