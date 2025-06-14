package br.com.autobots.automanager.excecoes;

public class UsuarioNaoAutorizadoExcecao extends RuntimeException {
  public UsuarioNaoAutorizadoExcecao() {
    super("Usuário não autorizado");
  }
}
