package br.com.autobots.sistema.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.sistema.entidades.Usuario;
import br.com.autobots.sistema.enums.PerfilUsuario;
import br.com.autobots.sistema.excecoes.NaoEncontradoExcecao;
import br.com.autobots.sistema.excecoes.UsuarioNaoAutorizadoExcecao;
import br.com.autobots.sistema.provedores.AutenticacaoProvedor;
import br.com.autobots.sistema.repositorios.UsuarioRepositorio;

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
