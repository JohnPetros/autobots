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
@Entity(name = "enderecos")
public class Endereco extends RepresentationModel<Endereco> {
  @Id()
  @Schema(description = "ID do endereço", example = "1")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = true)
  @Schema(description = "Estado do endereço", example = "SP")
  private String estado;

  @Column(nullable = false)
  @Schema(description = "Cidade do endereço", example = "São Paulo")
  private String cidade;

  @Column(nullable = true)
  @Schema(description = "Bairro do endereço", example = "Jardim Paulista")
  private String bairro;

  @Column(nullable = false)
  @Schema(description = "Rua do endereço", example = "Rua das Flores")
  private String rua;

  @Column(nullable = false)
  @Schema(description = "Número do endereço", example = "123")
  private String numero;

  @Column(nullable = true)
  @Schema(description = "Código postal do endereço", example = "04101-300")
  private String codigoPostal;

  @Column(unique = false, nullable = true)
  @Schema(description = "Informações adicionais do endereço", example = "Apto 101")
  private String informacoesAdicionais;

  @Column(name = "cliente_id")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private Long clienteId;
}