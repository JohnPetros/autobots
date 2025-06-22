package br.com.autobots.usuarios.excecoes;

public class AutenticacaoExcecao extends RuntimeException {
  public AutenticacaoExcecao(String message) {
    super(message);
  }
}