package br.com.autobots.vendas.entidades;

import br.com.autobots.vendas.enums.PerfilUsuario;

import lombok.Data;

@Data
public class Usuario {
  private Long id;

  private String nome;

  private String email;

  private PerfilUsuario perfil;

  private Boolean inativo;
}