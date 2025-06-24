package br.com.autobots.sistema.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.sistema.entidades.Usuario;
import br.com.autobots.sistema.enums.PerfilUsuario;
import br.com.autobots.sistema.excecoes.NaoEncontradoExcecao;
import br.com.autobots.sistema.excecoes.UsuarioNaoAutorizadoExcecao;
import br.com.autobots.sistema.repositorios.UsuarioRepositorio;
import br.com.autobots.sistema.provedores.AutenticacaoProvedor;

@Service
public class AtualizaUsuarioServico {
  @Autowired
  private VerificaStringNuloServico verificaStringServico;

  @Autowired
  private AtualizaEnderecoServico atualizaEnderoServico;

  @Autowired
  private ValidaUsuarioServico validaUsuarioServico;

  @Autowired
  private UsuarioRepositorio usuarioRepositorio;

  @Autowired
  private AutenticacaoProvedor autenticacaoProvedor;

  public void atualizar(Usuario usuarioAtualizado) {
    var usuario = usuarioRepositorio.findById(usuarioAtualizado.getId());
    if (usuario.isEmpty()) {
      throw new NaoEncontradoExcecao("Usuario não encontrado");
    }

    switch (autenticacaoProvedor.getUsuario().getPerfil()) {
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

    validaUsuarioServico.validar(usuarioAtualizado);
    atualizarUsuario(usuario.get(), usuarioAtualizado);
    usuarioRepositorio.save(usuario.get());
  }

  private void atualizarUsuario(Usuario usuario, Usuario usuarioAtualizado) {
    atualizarUsuarioDados(usuario, usuarioAtualizado);
    if (usuarioAtualizado.getDocumentos() != null) {
      usuario.getDocumentos().clear();
      usuario.getDocumentos().addAll(usuarioAtualizado.getDocumentos());
    }
    if (usuarioAtualizado.getTelefones() != null) {
      usuario.getTelefones().clear();
      usuario.getTelefones().addAll(usuarioAtualizado.getTelefones());
    }
    atualizaEnderoServico.atualizar(usuario.getEndereco(), usuarioAtualizado.getEndereco());
  }

  private void atualizarUsuarioDados(Usuario usuario, Usuario usuarioAtualizado) {
    if (!verificaStringServico.verificar(usuarioAtualizado.getNome())) {
      usuario.setNome(usuarioAtualizado.getNome());
    }
    if (!verificaStringServico.verificar(usuarioAtualizado.getNomeSocial())) {
      usuario.setNomeSocial(usuarioAtualizado.getNomeSocial());
    }
    if (usuarioAtualizado.getPerfil() != null) {
      usuario.setPerfil(usuarioAtualizado.getPerfil());
    }
    if (usuarioAtualizado.getInativo() != null) {
      usuario.setInativo(usuarioAtualizado.getInativo());
    }
    if (usuarioAtualizado.getUltimoAcesso() != null) {
      usuario.setUltimoAcesso(usuarioAtualizado.getUltimoAcesso());
    }
  }

}
