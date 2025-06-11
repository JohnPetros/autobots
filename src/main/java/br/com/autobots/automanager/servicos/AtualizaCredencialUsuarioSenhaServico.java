package br.com.autobots.automanager.servicos;

import org.springframework.stereotype.Service;

import br.com.autobots.automanager.entidades.CredencialUsuarioSenha;

@Service
public class AtualizaCredencialUsuarioSenhaServico {
  public void atualizar(
      CredencialUsuarioSenha credencialUsuarioSenha,
      CredencialUsuarioSenha credencialUsuarioSenhaAtualizada) {
    if (credencialUsuarioSenhaAtualizada == null) {
      return;
    }
    if (credencialUsuarioSenhaAtualizada.getNomeUsuario() != null) {
      credencialUsuarioSenha.setNomeUsuario(credencialUsuarioSenhaAtualizada.getNomeUsuario());
    }
    if (credencialUsuarioSenhaAtualizada.getSenha() != null) {
      credencialUsuarioSenha.setSenha(credencialUsuarioSenhaAtualizada.getSenha());
    }
    if (credencialUsuarioSenhaAtualizada.getCriacao() != null) {
      credencialUsuarioSenha.setCriacao(credencialUsuarioSenhaAtualizada.getCriacao());
    }
  }
}
