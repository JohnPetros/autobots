package br.com.autobots.automanager.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.automanager.entidades.Documento;
import br.com.autobots.automanager.excecoes.ConflitoExcecao;
import br.com.autobots.automanager.repositorios.DocumentoRepositorio;

@Service
public class ValidaDocumentoServico {
  @Autowired
  private DocumentoRepositorio documentoRepositorio;

  public void validar(Documento documento) {
    var documentoExistente = documentoRepositorio.findByNumero(documento.getNumero());
    if (documentoExistente.isPresent()) {
      throw new ConflitoExcecao("Documento já cadastrado: " + documento.getNumero());
    }
  }

}
