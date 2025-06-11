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
    if (documento == null) {
      return;
    }
    if (documento.getNumero() != null) {
      var documentoExistente = documentoRepositorio.findByNumero(documento.getNumero());
      if (documentoExistente.isPresent()) {
        throw new ConflitoExcecao("Documento já cadastrado: " + documento.getNumero());
      }
    }

    switch (documento.getTipo()) {
      case CPF:
        if (documento.getNumero().length() != 11) {
          throw new ConflitoExcecao("CPF deve conter 11 dígitos");
        }
        break;
      case CNPJ:
        if (documento.getNumero().length() != 14) {
          throw new ConflitoExcecao("CNPJ deve conter 14 dígitos");
        }
        break;
      case RG:
        if (documento.getNumero().length() != 9) {
          throw new ConflitoExcecao("RG deve conter 9 dígitos");
        }
        break;
      case CNH:
        if (documento.getNumero().length() != 11) {
          throw new ConflitoExcecao("CNH deve conter 11 dígitos");
        }
        break;
      case PASSAPORTE:
        if (documento.getNumero().length() != 9) {
          throw new ConflitoExcecao("Passaporte deve conter 9 dígitos");
        }
        break;
    }
  }
}
