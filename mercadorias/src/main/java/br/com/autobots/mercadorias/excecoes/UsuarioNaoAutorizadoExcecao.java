package br.com.autobots.mercadorias.excecoes;

public class UsuarioNaoAutorizadoExcecao extends RuntimeException {
  public UsuarioNaoAutorizadoExcecao() {
    super("Usuário não autorizado");
  }
}
