package br.com.autobots.automanager.entidades;

import java.util.Date;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
  @Schema(description = "ID da mercadoria", example = "1")
  private Long id;

  @Column(nullable = false)
  @Schema(description = "Data de validade da mercadoria", example = "2025-01-01", required = true)
  private Date validade;

  @Column(nullable = false)
  @Schema(description = "Data de fabricação da mercadoria", example = "2025-01-01", required = true)
  private Date fabricao;

  @Column(nullable = false)
  @Schema(description = "Data de cadastro da mercadoria", example = "2025-01-01", required = true)
  private Date cadastro;

  @Column(nullable = false)
  @Schema(description = "Nome da mercadoria", example = "Óleo de motor", required = true)
  private String nome;

  @Column(nullable = false)
  @Schema(description = "Quantidade da mercadoria", example = "10", required = true)
  private long quantidade;

  @Column(nullable = false)
  @Schema(description = "Valor da mercadoria", example = "100.00", required = true)
  private double valor;

  @Column()
  @Schema(description = "Descrição da mercadoria", example = "Óleo de motor 10W40", required = true)
  private String descricao;
}