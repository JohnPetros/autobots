package br.com.autobots.vendas.entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "vendas")
public class Venda extends RepresentationModel<Venda> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "ID da venda", example = "1")
  private Long id;

  @Column(nullable = false)
  @Schema(description = "Data de cadastro da venda", example = "2025-01-01")
  @NotNull(message = "Data de cadastro da venda é obrigatória")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @Past(message = "A data de cadastro deve ser no passado")
  private LocalDate cadastro;

  @Column(nullable = false, unique = true)
  @Schema(description = "Identificação da venda", example = "1234567890")
  private String identificacao;

  @Column(name = "cliente_id", nullable = false)
  @Schema(description = "Cliente da venda")
  @NotNull(message = "ID do cliente da venda é obrigatório")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private Long clienteId;

  @Column(name = "vendedor_id", nullable = false)
  @Schema(description = "Vendedor da venda")
  @NotNull(message = "ID do vendedor da venda é obrigatório")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private Long vendedorId;

  @Column(name = "pecasids", nullable = false)
  @Schema(description = "Pecas da venda")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private List<Long> pecasIds = new ArrayList<>();

  @Column(name = "servicos_ids", nullable = false)
  @Schema(description = "Serviços da venda")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private List<Long> servicosIds = new ArrayList<>();

  @Column(name = "veiculo_id", nullable = false)
  @Schema(description = "Veículo da venda")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private Long veiculoId;

  @Column(name = "empresa_id", nullable = false)
  @Schema(description = "Empresa da venda")
  private Long empresaId;

  @Transient
  private Usuario cliente;

  @Transient
  private Usuario vendedor;

  @Transient
  private List<Peca> pecas = new ArrayList<>();

  @Transient
  private List<Servico> servicos = new ArrayList<>();

  @Transient
  private Veiculo veiculo;
}
