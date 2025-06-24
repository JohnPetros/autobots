package br.com.autobots.sistema.excecoes;

public class AutenticacaoExcecao extends RuntimeException {
  public AutenticacaoExcecao(String message) {
    super(message);
  }
}