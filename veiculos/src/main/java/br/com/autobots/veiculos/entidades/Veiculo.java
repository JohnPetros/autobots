package br.com.autobots.veiculos.entidades;

import org.springframework.hateoas.RepresentationModel;

import br.com.autobots.veiculos.enums.TipoVeiculo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Validated
@Entity(name = "veiculos")
public class Veiculo extends RepresentationModel<Veiculo> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "ID do veículo", example = "1")
  private Long id;

  @Column(nullable = false)
  @Schema(description = "Tipo do veículo", example = "HATCH")
  @NotNull(message = "Erro: O tipo do veículo é obrigatório e não pode ser nulo")
  private TipoVeiculo tipo;

  @Column(nullable = false)
  @Schema(description = "Modelo do veículo", example = "Gol")
  @NotBlank(message = "Modelo do veículo é obrigatório")
  private String modelo;

  @Column(nullable = false)
  @Schema(description = "Placa do veículo", example = "ABC-1234")
  @NotBlank(message = "Placa do veículo é obrigatória")
  @jakarta.validation.constraints.Pattern(regexp = "[A-Z]{3}-\\d{4}", message = "Placa deve ser no formato ABC-1234")
  private String placa;

  @Column(nullable = false)
  @Schema(description = "ID do proprietário", example = "1")
  @NotNull(message = "ID do proprietário é obrigatório")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private Long proprietarioId;

  @Transient
  private Usuario proprietario;

  @Column(nullable = false)
  @Schema(description = "ID da empresa", example = "1")
  @NotNull(message = "ID da empresa é obrigatório")
  private Long empresaId;
}
