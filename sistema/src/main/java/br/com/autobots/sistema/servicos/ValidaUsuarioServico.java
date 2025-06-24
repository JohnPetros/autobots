package br.com.autobots.sistema.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.sistema.entidades.Usuario;

@Service
public class ValidaUsuarioServico {
  @Autowired
  private ValidaDocumentoServico validaDocumentoServico;

  public void validar(Usuario usuario) {
    for (var documento : usuario.getDocumentos()) {
      validaDocumentoServico.validar(documento);
    }
  }
}
