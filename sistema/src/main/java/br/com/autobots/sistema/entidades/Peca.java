package br.com.autobots.sistema.entidades;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
@Entity(name = "pecas")
public class Peca extends RepresentationModel<Peca> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "ID da peca", example = "1")
  private Long id;

  @Column(nullable = false)
  @Schema(description = "Data de validade da peca", example = "2025-01-01", required = true)
  @NotNull(message = "Data de validade da peca é obrigatória")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @Future(message = "A data de validade deve ser no futuro")
  private LocalDate validade;

  @Column(nullable = false)
  @Schema(description = "Data de fabricação da peca", example = "2025-01-01", required = true)
  @NotNull(message = "Data de fabricação da peca é obrigatória")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @Past(message = "A data de cadastro deve ser no passado")
  private LocalDate fabricao;

  @Column(nullable = false)
  @Schema(description = "Data de cadastro da peca", example = "2025-01-01", required = true)
  @NotNull(message = "Data de cadastro da peca é obrigatória")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate cadastro;

  @Column(nullable = false)
  @Schema(description = "Nome da peca", example = "Óleo de motor", required = true)
  @NotBlank(message = "Nome da peca é obrigatório")
  private String nome;

  @Column(nullable = false)
  @Schema(description = "Quantidade da peca", example = "10", required = true)
  @NotNull(message = "Quantidade da peca é obrigatória")
  @Min(value = 0, message = "Quantidade da peca deve ser maior que 0")
  private long quantidade;

  @Column(nullable = false)
  @Schema(description = "Valor da peca", example = "100.00", required = true)
  @NotNull(message = "Valor da peca é obrigatório")
  @Min(value = 0, message = "Valor da peca deve ser maior que 0")
  private double valor;

  @Column(nullable = false)
  @Schema(description = "Descrição da peca", example = "Óleo de motor 10W40", required = true)
  @NotBlank(message = "Descrição da peca é obrigatória")
  private String descricao;

  @Column(nullable = false)
  @Schema(description = "ID da empresa", example = "1")
  @NotNull(message = "Empresa é obrigatória")
  private Long empresaId;
}