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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "clientes")
public class Cliente extends RepresentationModel<Cliente> {
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