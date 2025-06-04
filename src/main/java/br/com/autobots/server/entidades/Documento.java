package br.com.autobots.server.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "documentos")
public class Documento {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Schema(description = "ID do documento", example = "1")
  private Long id;
  @Column
  @Schema(description = "Tipo do documento", example = "CPF")
  private String tipo;
  @Column(unique = true)
  @Schema(description = "Número do documento", example = "1234567890")
  private String numero;
}