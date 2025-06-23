package br.com.autobots.vendas.entidades;

import lombok.Data;

@Data
public class Servico {
  private Long id;

  private String nome;

  private Double valor;

  private String descricao;

  private Long empresaId;
}
