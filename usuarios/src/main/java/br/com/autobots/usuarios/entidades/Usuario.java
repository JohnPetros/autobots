package br.com.autobots.usuarios.entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.autobots.usuarios.enums.PerfilUsuario;
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
import jakarta.persistence.Transient;
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
  @Schema(description = "ID do usuário", example = "1")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  @Schema(description = "Nome do usuário", example = "João da Silva")
  @NotBlank(message = "Nome é obrigatório")
  private String nome;

  @Column(nullable = false)
  @Schema(description = "Email do usuário", example = "joao.silva@gmail.com")
  @NotBlank(message = "Email é obrigatório")
  private String email;

  @Transient
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String senha;

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

  @Column(nullable = false)
  @Schema(description = "ID da empresa", example = "1")
  private Long empresaId;
}