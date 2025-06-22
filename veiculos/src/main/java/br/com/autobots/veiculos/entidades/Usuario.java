package br.com.autobots.veiculos.entidades;

import br.com.autobots.veiculos.enums.PerfilUsuario;

import lombok.Data;

@Data
public class Usuario {
  private String nome;

  private String email;

  private PerfilUsuario perfil;

  private Boolean inativo;
}