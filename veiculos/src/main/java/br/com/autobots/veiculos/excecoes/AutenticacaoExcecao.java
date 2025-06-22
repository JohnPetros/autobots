package br.com.autobots.veiculos.excecoes;

public class AutenticacaoExcecao extends RuntimeException {
  public AutenticacaoExcecao(String message) {
    super(message);
  }
}