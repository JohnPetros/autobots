package br.com.autobots.automanager.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.automanager.entidades.Usuario;
import br.com.autobots.automanager.enums.PerfilUsuario;
import br.com.autobots.automanager.excecoes.NaoEncontradoExcecao;
import br.com.autobots.automanager.excecoes.UsuarioNaoAutorizadoExcecao;
import br.com.autobots.automanager.provedores.AutenticacaoProvedor;
import br.com.autobots.automanager.repositorios.EmpresaRepositorio;
import br.com.autobots.automanager.repositorios.UsuarioRepositorio;

import br.com.autobots.automanager.repositorios.CredencialRepositorio;

@Service
public class ExcluirUsuarioServico {
  @Autowired
  private EmpresaRepositorio empresaRepositorio;

  @Autowired
  private UsuarioRepositorio usuarioRepositorio;

  @Autowired
  private CredencialRepositorio credencialRepositorio;

  @Autowired
  private AutenticacaoProvedor autenticacaoProvedor;

  public void excluir(long empresaId, Usuario usuarioExcluido) {
    var empresa = empresaRepositorio.findById(empresaId);
    if (empresa.isEmpty()) {
      throw new NaoEncontradoExcecao("Empresa não encontrada");
    }
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
    credencialRepositorio.delete(usuario.get().getCredencial());
    empresa.get().getUsuarios().remove(usuario.get());
    empresaRepositorio.save(empresa.get());
  }

}
