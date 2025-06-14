package br.com.autobots.automanager.entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.autobots.automanager.enums.PerfilUsuario;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "clientes")
public class Usuario extends RepresentationModel<Usuario> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "ID do usuário", example = "1")
  private Long id;

  @Column(nullable = false)
  @Schema(description = "Nome do usuário", example = "João da Silva")
  @NotBlank(message = "Nome é obrigatório")
  private String nome;

  @Column
  @Schema(description = "Nome social do usuário", example = "João")
  @NotBlank(message = "Nome social é obrigatório")
  private String nomeSocial;

  @Enumerated(EnumType.STRING)
  @Schema(description = "Perfil do usuário", example = "FUNCIONARIO")
  @NotNull(message = "Perfil é obrigatório")
  private PerfilUsuario perfil;

  @OneToOne(optional = true, cascade = CascadeType.ALL)
  @JoinColumn(name = "endereco_id", nullable = true)
  @NotNull(message = "Endereço é obrigatório")
  private Endereco endereco;

  @Column(nullable = false)
  private Boolean inativo;

  @Column(nullable = true)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate ultimoAcesso = LocalDate.now();

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
  @JoinColumn(name = "cliente_id")
  @Schema(description = "Lista de documentos do usuário")
  @NotEmpty(message = "Documentos são obrigatórios")
  private List<Documento> documentos = new ArrayList<>();

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
  @JoinColumn(name = "cliente_id")
  @Schema(description = "Lista de telefones do usuário")
  @NotEmpty(message = "Telefones são obrigatórios")
  private List<Telefone> telefones = new ArrayList<>();

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "cliente_id")
  @Schema(description = "Lista de emails do usuário")
  @NotEmpty(message = "Emails são obrigatórios")
  private List<Email> emails = new ArrayList<>();

  @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @Schema(description = "Credencial de usuário e senha")
  @NotNull(message = "Credencial de usuário e senha é obrigatória")
  private Credencial credencial;

  @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
  @JoinColumn(name = "cliente_id")
  @Schema(description = "Lista de veículos do usuário")
  private List<Veiculo> veiculos = new ArrayList<>();

  @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
  @JoinColumn(name = "cliente_id")
  @Schema(description = "Lista de vendas do usuário")
  @JsonIgnore
  private List<Venda> vendas = new ArrayList<>();

}