package br.com.autobots.automanager.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.automanager.repositorios.DocumentoRepositorio;

@Service
public class ValidaDocumentoServico {
  @Autowired
  private DocumentoRepositorio documentoRepositorio;

}
