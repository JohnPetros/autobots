package br.com.autobots.autenticacao.excecoes;

public class NaoEncontradoExcecao extends RuntimeException {
  public NaoEncontradoExcecao(String message) {
    super(message);
  }
}
