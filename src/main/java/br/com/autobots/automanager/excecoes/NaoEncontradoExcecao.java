package br.com.autobots.automanager.excecoes;

public class NaoEncontradoExcecao extends RuntimeException {
  public NaoEncontradoExcecao(String message) {
    super(message);
  }
}
