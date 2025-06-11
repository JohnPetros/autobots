package br.com.autobots.automanager.entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "empresas")
public class Empresa extends RepresentationModel<Empresa> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  @NotBlank(message = "Razão social é obrigatória")
  private String razaoSocial;

  @Column
  @NotBlank(message = "Nome fantasia é obrigatório")
  private String nomeFantasia;

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @NotEmpty(message = "Usuários são obrigatórios")
  private List<Telefone> telefones = new ArrayList<>();

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @NotNull(message = "Endereço é obrigatório")
  private Endereco endereco;

  @Column(nullable = false)
  @NotNull(message = "Data de cadastro é obrigatória")
  private Date cadastro;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @NotEmpty(message = "Usuários são obrigatórios")
  private List<Usuario> usuarios = new ArrayList<>();

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<Mercadoria> mercadorias = new ArrayList<>();

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<Servico> servicos = new ArrayList<>();

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<Venda> vendas = new ArrayList<>();
}
