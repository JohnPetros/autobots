package br.com.autobots.usuarios.servicos;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.usuarios.excecoes.NaoEncontradoExcecao;
import br.com.autobots.usuarios.excecoes.UsuarioNaoAutorizadoExcecao;
import br.com.autobots.usuarios.entidades.Usuario;
import br.com.autobots.usuarios.enums.PerfilUsuario;
import br.com.autobots.usuarios.provedores.AutenticacaoProvedor;
import br.com.autobots.usuarios.repositorios.UsuarioRepositorio;

@Service
public class ObterUsuarioServico {
  @Autowired
  private UsuarioRepositorio usuarioRepositorio;

  @Autowired
  private AutenticacaoProvedor autenticacaoProvedor;

  public Usuario obterUsuario(Long id) {
    var usuario = usuarioRepositorio.findById(id);
    if (usuario.isEmpty()) {
      throw new NaoEncontradoExcecao("Usuário não encontrado");
    }

    validarAutorizacao(usuario);

    return usuario.get();
  }

  public Usuario obterUsuarioPorEmail(String email) {
    var usuario = usuarioRepositorio.findByEmail(email);
    if (usuario.isEmpty()) {
      throw new NaoEncontradoExcecao("Usuário não encontrado");
    }

    validarAutorizacao(usuario);

    return usuario.get();
  }

  private void validarAutorizacao(Optional<Usuario> usuario) {
    var usuarioAutenticado = autenticacaoProvedor.getUsuario();
    if (usuarioAutenticado.getPerfil() == PerfilUsuario.CLIENTE) {
      if (usuarioAutenticado.getId() != usuario.get().getId()) {
        throw new UsuarioNaoAutorizadoExcecao();
      }
    }

    if (usuarioAutenticado.getPerfil() == PerfilUsuario.VENDEDOR
        && usuario.get().getPerfil() != PerfilUsuario.CLIENTE) {
      if (usuarioAutenticado.getId() != usuario.get().getId()) {
        throw new UsuarioNaoAutorizadoExcecao();
      }
    }

    if (usuarioAutenticado.getPerfil() == PerfilUsuario.GERENTE
        && usuario.get().getPerfil() == PerfilUsuario.ADMIN) {
      throw new UsuarioNaoAutorizadoExcecao();
    }
  }
}
