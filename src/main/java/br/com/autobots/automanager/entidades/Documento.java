package br.com.autobots.automanager.entidades;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class Documento extends RepresentationModel<Cliente> {
  @Id
  @Schema(description = "ID do documento", example = "1")
  private Long id;

  @Column
  @Schema(description = "Tipo do documento", example = "CPF")
  private String tipo;

  @Column(unique = true)
  @Schema(description = "Número do documento", example = "1234567890")
  private String numero;

  @Column(name = "cliente_id")
  private Long clienteId;
}