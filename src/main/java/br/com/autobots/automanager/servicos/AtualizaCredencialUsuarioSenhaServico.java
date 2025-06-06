package br.com.autobots.automanager.servicos;

import org.springframework.stereotype.Service;

import br.com.autobots.automanager.entidades.CredencialUsuarioSenha;

@Service
public class AtualizaCredencialUsuarioSenhaServico {
  public void atualizar(
      CredencialUsuarioSenha credencialUsuarioSenha,
      CredencialUsuarioSenha credencialUsuarioSenhaAtualizada) {
    credencialUsuarioSenha.setNomeUsuario(credencialUsuarioSenhaAtualizada.getNomeUsuario());
    credencialUsuarioSenha.setSenha(credencialUsuarioSenhaAtualizada.getSenha());
    credencialUsuarioSenha.setCriacao(credencialUsuarioSenhaAtualizada.getCriacao());
    credencialUsuarioSenha.setUltimoAcesso(credencialUsuarioSenhaAtualizada.getUltimoAcesso());
    credencialUsuarioSenha.setInativo(credencialUsuarioSenhaAtualizada.isInativo());
  }
}
