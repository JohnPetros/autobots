package br.com.autobots.automanager.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class CredencialCodigoBarra extends Credencial {
  @Column(nullable = false, unique = true)
  @NotNull(message = "Código de barras é obrigatório")
  private Long codigo;
}
