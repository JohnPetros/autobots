package br.com.autobots.vendas.excecoes;

public class UsuarioNaoAutorizadoExcecao extends RuntimeException {
  public UsuarioNaoAutorizadoExcecao() {
    super("Usuário não autorizado");
  }
}
