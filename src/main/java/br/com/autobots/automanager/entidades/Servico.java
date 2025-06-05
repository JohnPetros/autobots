
package br.com.autobots.automanager.entidades;

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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "servicos")
public class Servico extends RepresentationModel<Servico> {
  @Id
  @Schema(description = "ID do serviço", example = "1")
  private Long id;

  @Column(nullable = false)
  @Schema(description = "Nome do serviço", example = "Troca de óleo")
  private String nome;

  @Column(nullable = false)
  @Schema(description = "Valor do serviço", example = "100.00")
  private double valor;

  @Column
  @Schema(description = "Descrição do serviço", example = "Troca de óleo do motor")
  private String descricao;
}
