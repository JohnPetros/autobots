package br.com.autobots.vendas.entidades;

import lombok.Data;

@Data
public class Veiculo {
  private Long id;

  private String modelo;

  private String placa;
}
