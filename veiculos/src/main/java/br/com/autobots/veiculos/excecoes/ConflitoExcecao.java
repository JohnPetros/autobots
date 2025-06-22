package br.com.autobots.veiculos.excecoes;

public class ConflitoExcecao extends RuntimeException {
  public ConflitoExcecao(String message) {
    super(message);
  }
}
