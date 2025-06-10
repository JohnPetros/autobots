package br.com.autobots.automanager.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.automanager.entidades.Usuario;
import br.com.autobots.automanager.repositorios.UsuarioRepositorio;

@Service
public class ValidaUsuarioServico {
  @Autowired
  private UsuarioRepositorio repositorio;

  public boolean validar(Usuario usuario) {
    var usuarioExistente = repositorio.findByCredencialUsuarioSenha(usuario.getCredencialUsuarioSenha());

    if (usuarioExistente.isPresent()) {
      return false;
    }

    if (usuario.getCredencialCodigoBarra() != null) {
      usuarioExistente = repositorio.findByCredencialCodigoBarra(usuario.getCredencialCodigoBarra());
      if (usuarioExistente.isPresent()) {
        return false;
      }
    }

    usuarioExistente = repositorio.findByDocumentos(usuario.getDocumentos());
    if (usuarioExistente.isPresent()) {
      return false;
    }

    return true;
  }
}
