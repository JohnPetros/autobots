package br.com.autobots.mercadorias.entidades;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "servicos")
public class Servico extends RepresentationModel<Servico> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "ID do serviço", example = "1")
  private Long id;

  @Column(nullable = false)
  @Schema(description = "Nome do serviço", example = "Troca de óleo")
  @NotBlank(message = "Nome é obrigatório")
  private String nome;

  @Column(nullable = false)
  @Schema(description = "Valor do serviço", example = "100.00")
  @NotNull(message = "Valor é obrigatório")
  @Positive(message = "Valor deve ser maior que 0")
  private Double valor;

  @Column
  @Schema(description = "Descrição do serviço", example = "Troca de óleo do motor")
  @NotBlank(message = "Descrição é obrigatória")
  private String descricao;

  @Column(nullable = false)
  @Schema(description = "ID da empresa", example = "1")
  @NotNull(message = "Empresa é obrigatória")
  private Long empresaId;
}
