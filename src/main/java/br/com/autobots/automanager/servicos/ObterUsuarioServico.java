package br.com.autobots.automanager.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.automanager.excecoes.NaoEncontradoExcecao;
import br.com.autobots.automanager.excecoes.UsuarioNaoAutorizadoExcecao;
import br.com.autobots.automanager.entidades.Usuario;
import br.com.autobots.automanager.enums.PerfilUsuario;
import br.com.autobots.automanager.provedores.AutenticacaoProvedor;
import br.com.autobots.automanager.repositorios.UsuarioRepositorio;

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

    return usuario.get();
  }
}
