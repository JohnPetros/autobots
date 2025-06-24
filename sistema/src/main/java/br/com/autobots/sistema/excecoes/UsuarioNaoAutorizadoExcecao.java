package br.com.autobots.sistema.excecoes;

public class UsuarioNaoAutorizadoExcecao extends RuntimeException {
  public UsuarioNaoAutorizadoExcecao() {
    super("Usuário não autorizado");
  }
}
