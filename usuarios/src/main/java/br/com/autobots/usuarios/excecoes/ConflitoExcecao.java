package br.com.autobots.usuarios.excecoes;

public class ConflitoExcecao extends RuntimeException {
  public ConflitoExcecao(String message) {
    super(message);
  }
}
