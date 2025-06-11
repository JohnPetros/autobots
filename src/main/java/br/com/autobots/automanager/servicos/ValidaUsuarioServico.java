package br.com.autobots.automanager.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.automanager.entidades.Usuario;
import br.com.autobots.automanager.excecoes.ConflitoExcecao;
import br.com.autobots.automanager.repositorios.CredencialCodigoBarraRepositorio;
import br.com.autobots.automanager.repositorios.CredencialUsuarioSenhaRepositorio;

@Service
public class ValidaUsuarioServico {
  @Autowired
  private CredencialUsuarioSenhaRepositorio credencialUsuarioSenhaRepositorio;

  @Autowired
  private CredencialCodigoBarraRepositorio credencialCodigoBarraRepositorio;

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
    if (usuario.getCredencialUsuarioSenha() == null) {
      return;
    }

    var usuarioExistente = credencialUsuarioSenhaRepositorio
        .findByNomeUsuario(usuario.getCredencialUsuarioSenha().getNomeUsuario());

    if (usuarioExistente.isPresent()) {
      throw new ConflitoExcecao("Usuario já cadastrado com esse nome de usuário");
    }
  }

  private void validarCredencialCodigoBarra(Usuario usuario) {
    if (usuario.getCredencialCodigoBarra() == null) {
      return;
    }

    var usuarioExistente = credencialCodigoBarraRepositorio
        .findByCodigo(usuario.getCredencialCodigoBarra().getCodigo());

    if (usuarioExistente.isPresent()) {
      throw new ConflitoExcecao("Usuario já cadastrado com esse código de barras");
    }
  }
}
