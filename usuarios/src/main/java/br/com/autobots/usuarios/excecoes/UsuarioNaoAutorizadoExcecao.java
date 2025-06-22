package br.com.autobots.usuarios.excecoes;

public class UsuarioNaoAutorizadoExcecao extends RuntimeException {
  public UsuarioNaoAutorizadoExcecao() {
    super("Usuário não autorizado");
  }
}
