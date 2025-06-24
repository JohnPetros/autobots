package br.com.autobots.automanager.entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;

@Getter
@Setter
@Entity
@Table(name = "clientes")
public class Cliente extends RepresentationModel<Cliente> {
  @Id
  @Schema(description = "ID do cliente", example = "1")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  @Schema(description = "Nome do cliente", example = "João da Silva")
  private String nome;

  @Column
  @Schema(description = "Nome social do cliente", example = "João da Silva")
  private String nomeSocial;

  @Column
  @Schema(description = "Data de nascimento do cliente", example = "1990-01-01")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate dataNascimento;

  @Column
  @Schema(description = "Data de cadastro do cliente", example = "2021-01-01")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate dataCadastro;

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
  @JoinColumn(name = "cliente_id")
  @Schema(description = "Lista de documentos do cliente")
  private List<Documento> documentos = new ArrayList<>();

  @OneToOne(optional = true, cascade = CascadeType.ALL)
  @JoinColumn(name = "endereco_id", nullable = true)
  private Endereco endereco;

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
  @JoinColumn(name = "cliente_id")
  @Schema(description = "Lista de telefones do cliente")
  private List<Telefone> telefones = new ArrayList<>();
}