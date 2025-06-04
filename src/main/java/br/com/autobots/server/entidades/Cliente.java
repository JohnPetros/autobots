package br.com.autobots.server.entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Entity(name = "clientes")
public class Cliente {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "ID do cliente", example = "1")
  private Long id;
  @Column
  @Schema(description = "Nome do cliente", example = "João da Silva")
  private String nome;
  @Column
  @Schema(description = "Nome social do cliente", example = "João")
  private String nomeSocial;
  @Column
  @Schema(description = "Data de nascimento do cliente", example = "1990-01-01")
  private LocalDate dataNascimento;
  @Column
  @Schema(description = "Data de cadastro do cliente", example = "2021-01-01")
  private LocalDate dataCadastro;
  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
  @Schema(description = "Documentos do cliente")
  private List<Documento> documentos = new ArrayList<>();
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @Schema(description = "Endereço do cliente")
  private Endereco endereco;
  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
  @Schema(description = "Telefones do cliente")
  private List<Telefone> telefones = new ArrayList<>();
}