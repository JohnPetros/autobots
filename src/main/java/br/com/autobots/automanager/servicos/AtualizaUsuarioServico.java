package br.com.autobots.automanager.servicos;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.automanager.entidades.Usuario;
import br.com.autobots.automanager.enums.PerfilUsuario;
import br.com.autobots.automanager.excecoes.NaoEncontradoExcecao;
import br.com.autobots.automanager.excecoes.UsuarioNaoAutorizadoExcecao;
import br.com.autobots.automanager.provedores.AutenticacaoProvedor;
import br.com.autobots.automanager.repositorios.EmpresaRepositorio;
import br.com.autobots.automanager.repositorios.UsuarioRepositorio;

@Service
public class AtualizaUsuarioServico {
  @Autowired
  private VerificaStringNuloServico verificaStringServico;

  @Autowired
  private AtualizaEnderecoServico atualizaEnderoServico;

  @Autowired
  private AtualizaCredencialServico atualizaCredencialServico;

  @Autowired
  private ValidaUsuarioServico validaUsuarioServico;

  @Autowired
  private EmpresaRepositorio empresaRepositorio;

  @Autowired
  private UsuarioRepositorio usuarioRepositorio;

  @Autowired
  private AutenticacaoProvedor autenticacaoProvedor;

  public void atualizar(long empresaId, Usuario usuarioAtualizado) {
    var empresa = empresaRepositorio.findById(empresaId);
    if (empresa.isEmpty()) {
      throw new NaoEncontradoExcecao("Empresa não encontrada");
    }
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
    if (usuarioAtualizado.getEmails() != null) {
      usuario.getEmails().clear();
      usuario.getEmails().addAll(usuarioAtualizado.getEmails());
    }
    if (usuarioAtualizado.getDocumentos() != null) {
      usuario.getDocumentos().clear();
      usuario.getDocumentos().addAll(usuarioAtualizado.getDocumentos());
    }
    if (usuarioAtualizado.getTelefones() != null) {
      usuario.getTelefones().clear();
      usuario.getTelefones().addAll(usuarioAtualizado.getTelefones());
    }
    if (usuarioAtualizado.getVeiculos() != null) {
      usuario.getVeiculos().clear();
      usuario.getVeiculos().addAll(usuarioAtualizado.getVeiculos());
    }
    atualizaEnderoServico.atualizar(usuario.getEndereco(), usuarioAtualizado.getEndereco());
    atualizaCredencialServico.atualizar(
        usuario.getCredencial(),
        usuarioAtualizado.getCredencial());
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
