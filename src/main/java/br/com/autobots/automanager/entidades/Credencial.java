package br.com.autobots.automanager.entidades;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Credencial {
  @Id
  private Long id;

  @Column(nullable = false)
  private LocalDate criacao;

  @Column
  private LocalDate ultimoAcesso;

  @Column(nullable = false)
  private boolean inativo;
}