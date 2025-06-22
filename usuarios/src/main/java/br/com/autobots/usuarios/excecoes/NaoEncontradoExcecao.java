package br.com.autobots.usuarios.excecoes;

public class NaoEncontradoExcecao extends RuntimeException {
  public NaoEncontradoExcecao(String message) {
    super(message);
  }
}
