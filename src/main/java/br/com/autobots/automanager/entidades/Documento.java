package br.com.autobots.automanager.entidades;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  @Schema(description = "Tipo do documento", example = "CPF")
  private String tipo;

  @Column(unique = true, nullable = false)
  @Schema(description = "Número do documento", example = "1234567890")
  private String numero;

  @Column(name = "cliente_id")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private Long clienteId;
}