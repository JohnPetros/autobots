package br.com.autobots.automanager.servicos;

import java.util.List;

public interface AdicionaLinkServico<T> {
  public List<T> adicionarLink(List<T> lista);

  public T adicionarLink(T objeto);
}