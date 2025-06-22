package br.com.autobots.veiculos.excecoes;

public class NaoEncontradoExcecao extends RuntimeException {
  public NaoEncontradoExcecao(String message) {
    super(message);
  }
}
