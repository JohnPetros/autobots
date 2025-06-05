package br.com.autobots.automanager.entidades;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import br.com.autobots.automanager.enums.TipoVeiculo;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Veiculo extends RepresentationModel<Veiculo> {
  @Id
  @Schema(description = "ID do veículo", example = "1")
  private Long id;

  @Column(nullable = false)
  @Schema(description = "Tipo do veículo", example = "HATCH")
  private TipoVeiculo tipo;

  @Column(nullable = false)
  @Schema(description = "Modelo do veículo", example = "Gol")
  private String modelo;

  @Column(nullable = false)
  @Schema(description = "Placa do veículo", example = "ABC-1234")
  private String placa;

  @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
  private Usuario proprietario;

  @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
  private List<Venda> vendas = new ArrayList<>();
}
