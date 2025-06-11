package br.com.autobots.automanager.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.automanager.entidades.Usuario;
import br.com.autobots.automanager.excecoes.ConflitoExcecao;
import br.com.autobots.automanager.repositorios.CredencialCodigoBarraRepositorio;
import br.com.autobots.automanager.repositorios.CredencialUsuarioSenhaRepositorio;
import br.com.autobots.automanager.repositorios.UsuarioRepositorio;

@Service
public class ValidaUsuarioServico {
  @Autowired
  private UsuarioRepositorio repositorio;

  @Autowired
  private ValidaDocumentoServico validaDocumentoServico;

  @Autowired
  private CredencialUsuarioSenhaRepositorio credencialUsuarioSenhaRepositorio;

  @Autowired
  private CredencialCodigoBarraRepositorio credencialCodigoBarraRepositorio;

  public void validar(Usuario usuario) {
    var usuarioExistente = credencialUsuarioSenhaRepositorio
        .findByNomeUsuario(usuario.getCredencialUsuarioSenha().getNomeUsuario());

    if (usuarioExistente.isPresent()) {
      throw new ConflitoExcecao("Usuario já cadastrado com esse nome de usuário");
    }

    var usuarioExistenteCodigoBarra = credencialCodigoBarraRepositorio
        .findByCodigo(usuario.getCredencialCodigoBarra().getCodigo());

    if (usuarioExistenteCodigoBarra.isPresent()) {
      throw new ConflitoExcecao("Usuario já cadastrado com esse código de barras");
    }

    for (var documento : usuario.getDocumentos()) {
      validaDocumentoServico.validar(documento);
    }
  }
}
