package br.com.autobots.server.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Entity(name = "enderecos")
public class Endereco {
  @Id()
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "ID do endereço", example = "1")
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
  @Schema(description = "CEP do endereço", example = "01001-000")
  private String codigoPostal;
  @Column(unique = false, nullable = true)
  @Schema(description = "Informações adicionais do endereço", example = "Apto 101")
  private String informacoesAdicionais;
  @Column(name = "cliente_id")
  @Schema(description = "ID do cliente proprietário do endereço", example = "1")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private Long clienteId;
}