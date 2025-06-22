package br.com.autobots.mercadorias.entidades;

import br.com.autobots.mercadorias.enums.PerfilUsuario;

import lombok.Data;

@Data
public class Usuario {
  private String nome;

  private String email;

  private PerfilUsuario perfil;

  private Boolean inativo;
}