package br.com.autobots.automanager.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Email {
  @Id
  private Long id;

  @Column(nullable = false)
  private String endereco;
}
