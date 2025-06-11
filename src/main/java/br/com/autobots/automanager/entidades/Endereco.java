package br.com.autobots.automanager.entidades;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
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
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "ID do endereço", example = "1")
  private Long id;

  @Column(nullable = true)
  @Schema(description = "Estado do endereço", example = "SP")
  @NotBlank(message = "Estado é obrigatório")
  private String estado;

  @Column(nullable = false)
  @Schema(description = "Cidade do endereço", example = "São Paulo")
  @NotBlank(message = "Cidade é obrigatória")
  private String cidade;

  @Column(nullable = true)
  @Schema(description = "Bairro do endereço", example = "Jardim Paulista")
  @NotBlank(message = "Bairro é obrigatório")
  private String bairro;

  @Column(nullable = false)
  @Schema(description = "Rua do endereço", example = "Rua das Flores")
  @NotBlank(message = "Rua é obrigatória")
  private String rua;

  @Column(nullable = false)
  @Schema(description = "Número do endereço", example = "123")
  @NotBlank(message = "Número é obrigatório")
  private String numero;

  @Column(nullable = true)
  @Schema(description = "Código postal do endereço", example = "04101-300")
  @NotBlank(message = "Código postal é obrigatório")
  private String codigoPostal;

  @Column(unique = false, nullable = true)
  @Schema(description = "Informações adicionais do endereço", example = "Apto 101")
  @NotBlank(message = "Informações adicionais são obrigatórias")
  private String informacoesAdicionais;
}