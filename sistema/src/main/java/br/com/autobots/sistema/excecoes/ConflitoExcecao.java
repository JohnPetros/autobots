package br.com.autobots.sistema.excecoes;

public class ConflitoExcecao extends RuntimeException {
  public ConflitoExcecao(String message) {
    super(message);
  }
}
