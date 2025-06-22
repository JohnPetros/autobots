package br.com.autobots.mercadorias.excecoes;

public class ConflitoExcecao extends RuntimeException {
  public ConflitoExcecao(String message) {
    super(message);
  }
}
