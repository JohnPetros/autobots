package br.com.autobots.usuarios.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.usuarios.entidades.Usuario;
import br.com.autobots.usuarios.enums.PerfilUsuario;
import br.com.autobots.usuarios.excecoes.NaoEncontradoExcecao;
import br.com.autobots.usuarios.excecoes.UsuarioNaoAutorizadoExcecao;
import br.com.autobots.usuarios.provedores.AutenticacaoProvedor;
import br.com.autobots.usuarios.repositorios.UsuarioRepositorio;

@Service
public class ExcluirUsuarioServico {
  @Autowired
  private UsuarioRepositorio usuarioRepositorio;

  @Autowired
  private AutenticacaoProvedor autenticacaoProvedor;

  public void excluir(Usuario usuarioExcluido) {
    var usuario = usuarioRepositorio.findById(usuarioExcluido.getId());
    if (usuario.isEmpty()) {
      throw new NaoEncontradoExcecao("Usuario não encontrado");
    }

    var perfil = autenticacaoProvedor.getUsuario().getPerfil();
    switch (perfil) {
      case VENDEDOR:
        if (usuario.get().getPerfil() != PerfilUsuario.CLIENTE) {
          throw new UsuarioNaoAutorizadoExcecao();
        }
        break;
      case GERENTE:
        if (usuario.get().getPerfil() == PerfilUsuario.ADMIN) {
          throw new UsuarioNaoAutorizadoExcecao();
        }
        break;
      default:
        break;
    }

    usuarioRepositorio.delete(usuario.get());
  }

}
