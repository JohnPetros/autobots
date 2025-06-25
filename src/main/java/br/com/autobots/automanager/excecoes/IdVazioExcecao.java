package br.com.autobots.automanager.excecoes;

public class IdVazioExcecao extends RuntimeException {
  public IdVazioExcecao() {
    super("Id é obrigatório");
  }
}
