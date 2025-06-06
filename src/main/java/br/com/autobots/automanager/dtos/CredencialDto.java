package br.com.autobots.automanager.dtos;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CredencialDto {
  private Long id;
  private LocalDate criacao;
  private LocalDate ultimoAcesso;
  private boolean inativo;
}
