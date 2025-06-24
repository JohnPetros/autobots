package br.com.autobots.sistema.servicos;

import java.util.List;

public interface AdicionaLinkServico<T> {
  public void adicionarLink(List<T> lista, Long empresaId);

  public void adicionarLink(T objeto, Long empresaId);
}