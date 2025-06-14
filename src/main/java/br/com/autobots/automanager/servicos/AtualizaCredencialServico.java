package br.com.autobots.automanager.servicos;

import org.springframework.stereotype.Service;

import br.com.autobots.automanager.entidades.Credencial;

@Service
public class AtualizaCredencialServico {
  public void atualizar(
      Credencial credencial,
      Credencial credencialAtualizada) {
    if (credencialAtualizada == null) {
      return;
    }
    if (credencialAtualizada.getNomeUsuario() != null) {
      credencial.setNomeUsuario(credencialAtualizada.getNomeUsuario());
    }
    if (credencialAtualizada.getSenha() != null) {
      credencial.setSenha(credencialAtualizada.getSenha());
    }
  }
}
