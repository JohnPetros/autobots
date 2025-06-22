package br.com.autobots.usuarios.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.usuarios.entidades.Usuario;

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
