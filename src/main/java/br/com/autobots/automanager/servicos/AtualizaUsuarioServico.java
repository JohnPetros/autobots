package br.com.autobots.automanager.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.automanager.entidades.Usuario;

@Service
public class AtualizaUsuarioServico {
  @Autowired
  private AtualizaEnderecoServico atualizaEnderoServico;

  @Autowired
  private AtualizaCredencialUsuarioSenhaServico atualizaCredencialUsuarioSenhaServico;

  @Autowired
  private AtualizaCredencialCodigoBarraServico atualizaCredencialCodigoBarraServico;

  public void atualizar(Usuario usuario, Usuario usuarioAtualizado) {
    atualizarDados(usuario, usuarioAtualizado);
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
    atualizaCredencialUsuarioSenhaServico.atualizar(
        usuario.getCredencialUsuarioSenha(),
        usuarioAtualizado.getCredencialUsuarioSenha());
    atualizaCredencialCodigoBarraServico.atualizar(
        usuario.getCredencialCodigoBarra(),
        usuarioAtualizado.getCredencialCodigoBarra());
  }

  private void atualizarDados(Usuario usuario, Usuario usuarioAtualizado) {
    if (usuarioAtualizado.getNome() != null) {
      usuario.setNome(usuarioAtualizado.getNome());
    }
    if (usuarioAtualizado.getNomeSocial() != null) {
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
