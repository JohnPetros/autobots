package br.com.autobots.vendas.excecoes;

public class AutenticacaoExcecao extends RuntimeException {
  public AutenticacaoExcecao(String message) {
    super(message);
  }
}