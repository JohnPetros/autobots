package br.com.autobots.automanager.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.automanager.entidades.Usuario;
import br.com.autobots.automanager.excecoes.ConflitoExcecao;
import br.com.autobots.automanager.repositorios.CredencialRepositorio;

@Service
public class ValidaUsuarioServico {
  @Autowired
  private CredencialRepositorio credencialRepositorio;

  @Autowired
  private ValidaDocumentoServico validaDocumentoServico;

  @Autowired
  private ValidaEmailServico validaEmailServico;

  public void validar(Usuario usuario) {
    validarCredencialUsuarioSenha(usuario);
    validarCredencialCodigoBarra(usuario);

    for (var documento : usuario.getDocumentos()) {
      validaDocumentoServico.validar(documento);
    }
    for (var email : usuario.getEmails()) {
      validaEmailServico.validar(email);
    }
  }

  private void validarCredencialUsuarioSenha(Usuario usuario) {
    if (usuario.getCredencial() == null) {
      return;
    }

    var usuarioExistente = credencialRepositorio
        .findByNomeUsuario(usuario.getCredencial().getNomeUsuario());

    if (usuarioExistente.isPresent()) {
      throw new ConflitoExcecao("Usuario já cadastrado com esse nome de usuário");
    }
  }

  private void validarCredencialCodigoBarra(Usuario usuario) {
    if (usuario.getCredencial() == null) {
      return;
    }

    var usuarioExistente = credencialRepositorio
        .findByNomeUsuario(usuario.getCredencial().getNomeUsuario());

    if (usuarioExistente.isPresent()) {
      throw new ConflitoExcecao("Usuario já cadastrado com esse código de barras");
    }
  }
}
