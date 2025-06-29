package br.com.autobots.automanager.entidades;

import br.com.autobots.automanager.enums.TipoDocumento;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "documentos")
public class Documento {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "ID do documento", example = "1")
  private Long id;

  @Column
  @Schema(description = "Tipo do documento", example = "CPF")
  @NotNull(message = "Tipo do documento é obrigatório")
  private TipoDocumento tipo;

  @Column(unique = true)
  @Schema(description = "Número do documento", example = "1234567890")
  @NotBlank(message = "Número do documento é obrigatório")
  private String numero;
}