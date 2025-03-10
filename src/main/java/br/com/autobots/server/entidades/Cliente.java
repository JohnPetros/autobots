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

@Data
@Entity(name = "clientes")
public class Cliente {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column
  private String nome;
  @Column
  private String nomeSocial;
  @Column
  private LocalDate dataNascimento;
  @Column
  private LocalDate dataCadastro;
  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
  private List<Documento> documentos = new ArrayList<>();
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  private Endereco endereco;
  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
  private List<Telefone> telefones = new ArrayList<>();
}