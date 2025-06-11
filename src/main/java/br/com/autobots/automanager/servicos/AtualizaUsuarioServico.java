package br.com.autobots.automanager.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.automanager.entidades.Usuario;

@Service
public class AtualizaUsuarioServico {
  @Autowired
  private VerificaStringNuloServico verificaStringServico;

  @Autowired
  private AtualizaEnderecoServico atualizaEnderoServico;

  @Autowired
  private AtualizaDocumentoServico atualizaDocumentoServico;

  @Autowired
  private AtualizaTelefoneServico atualizaTelefoneServico;

  @Autowired
  private AtualizaVeiculoServico atualizaVeiculoServico;

  @Autowired
  private AtualizaCredencialUsuarioSenhaServico atualizaCredencialUsuarioSenhaServico;

  @Autowired
  private AtualizaCredencialCodigoBarraServico atualizaCredencialCodigoBarraServico;

  public void atualizar(Usuario usuario, Usuario usuarioAtualizado) {
    atualizarDados(usuario, usuarioAtualizado);
    atualizaEnderoServico.atualizar(usuario.getEndereco(), usuarioAtualizado.getEndereco());
    atualizaDocumentoServico.atualizar(usuario.getDocumentos(), usuarioAtualizado.getDocumentos());
    atualizaTelefoneServico.atualizar(usuario.getTelefones(), usuarioAtualizado.getTelefones());
    atualizaVeiculoServico.atualizar(usuario.getVeiculos(), usuarioAtualizado.getVeiculos());
    atualizaCredencialUsuarioSenhaServico.atualizar(
        usuario.getCredencialUsuarioSenha(),
        usuarioAtualizado.getCredencialUsuarioSenha());
    atualizaCredencialCodigoBarraServico.atualizar(
        usuario.getCredencialCodigoBarra(),
        usuarioAtualizado.getCredencialCodigoBarra());
  }

  private void atualizarDados(Usuario usuario, Usuario usuarioAtualizado) {
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
