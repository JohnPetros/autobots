package br.com.autobots.automanager.entidades;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Entity(name = "mercadorias")
public class Mercadoria extends RepresentationModel<Mercadoria> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "ID da mercadoria", example = "1")
  private Long id;

  @Column(nullable = false)
  @Schema(description = "Data de validade da mercadoria", example = "2025-01-01", required = true)
  @NotNull(message = "Data de validade da mercadoria é obrigatória")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @Future(message = "A data de validade deve ser no futuro")
  private LocalDate validade;

  @Column(nullable = false)
  @Schema(description = "Data de fabricação da mercadoria", example = "2025-01-01", required = true)
  @NotNull(message = "Data de fabricação da mercadoria é obrigatória")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @Past(message = "A data de cadastro deve ser no passado")
  private LocalDate fabricao;

  @Column(nullable = false)
  @Schema(description = "Data de cadastro da mercadoria", example = "2025-01-01", required = true)
  @NotNull(message = "Data de cadastro da mercadoria é obrigatória")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate cadastro;

  @Column(nullable = false)
  @Schema(description = "Nome da mercadoria", example = "Óleo de motor", required = true)
  @NotBlank(message = "Nome da mercadoria é obrigatório")
  private String nome;

  @Column(nullable = false)
  @Schema(description = "Quantidade da mercadoria", example = "10", required = true)
  @NotNull(message = "Quantidade da mercadoria é obrigatória")
  @Min(value = 0, message = "Quantidade da mercadoria deve ser maior que 0")
  private long quantidade;

  @Column(nullable = false)
  @Schema(description = "Valor da mercadoria", example = "100.00", required = true)
  @NotNull(message = "Valor da mercadoria é obrigatório")
  @Min(value = 0, message = "Valor da mercadoria deve ser maior que 0")
  private double valor;

  @Column(nullable = false)
  @Schema(description = "Descrição da mercadoria", example = "Óleo de motor 10W40", required = true)
  @NotBlank(message = "Descrição da mercadoria é obrigatória")
  private String descricao;
}