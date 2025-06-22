package br.com.autobots.autenticacao.excecoes;

public class AutenticacaoExcecao extends RuntimeException {
  public AutenticacaoExcecao(String message) {
    super(message);
  }
}