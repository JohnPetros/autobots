package br.com.autobots.automanager.entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Entity
@Table(name = "clientes")
public class Cliente extends RepresentationModel<Cliente> {
  @Id
  @Schema(description = "ID do cliente", example = "1")
  private Long id;

  @Column
  @Schema(description = "Nome do cliente", example = "João da Silva")
  private String nome;

  @Column
  @Schema(description = "Nome social do cliente", example = "João da Silva")
  private String nomeSocial;

  @Column
  @Schema(description = "Data de nascimento do cliente", example = "1990-01-01")
  private LocalDate dataNascimento;

  @Column
  @Schema(description = "Data de cadastro do cliente", example = "2021-01-01")
  private LocalDate dataCadastro;

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
  @Schema(description = "Lista de documentos do cliente")
  private List<Documento> documentos = new ArrayList<>();

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @Schema(description = "Endereço do cliente")
  private Endereco endereco;

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
  @Schema(description = "Lista de telefones do cliente")
  private List<Telefone> telefones = new ArrayList<>();
}