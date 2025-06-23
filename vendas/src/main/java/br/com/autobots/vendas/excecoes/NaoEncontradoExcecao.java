package br.com.autobots.vendas.excecoes;

public class NaoEncontradoExcecao extends RuntimeException {
  public NaoEncontradoExcecao(String message) {
    super(message);
  }
}
