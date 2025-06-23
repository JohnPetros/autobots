package br.com.autobots.vendas.entidades;

import lombok.Data;

@Data
public class Peca {
  private Long id;

  private String nome;

  private long quantidade;

  private double valor;

  private String descricao;
}