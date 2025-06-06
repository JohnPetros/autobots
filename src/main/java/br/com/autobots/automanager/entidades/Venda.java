package br.com.autobots.automanager.entidades;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
  @Schema(description = "ID da venda", example = "1")
  private Long id;

  @Column(nullable = false)
  @Schema(description = "Data de cadastro da venda", example = "2025-01-01")
  private Date cadastro;

  @Column(nullable = false, unique = true)
  @Schema(description = "Identificação da venda", example = "1234567890")
  private String identificacao;

  @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
  @Schema(description = "Cliente da venda")
  private Usuario cliente;

  @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
  @Schema(description = "Funcionário da venda")
  private Usuario funcionario;

  @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
  @Schema(description = "Mercadorias da venda")
  private List<Mercadoria> mercadorias = new ArrayList<>();

  @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
  @Schema(description = "Serviços da venda")
  private List<Servico> servicos = new ArrayList<>();

  @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
  @Schema(description = "Veículo da venda")
  private Veiculo veiculo;
}
