package br.com.autobots.autenticacao.excecoes;

public class ConflitoExcecao extends RuntimeException {
  public ConflitoExcecao(String message) {
    super(message);
  }
}
