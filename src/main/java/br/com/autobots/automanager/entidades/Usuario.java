package br.com.autobots.automanager.entidades;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.autobots.automanager.enums.PerfilUsuario;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

@Getter
@Setter
@Entity
@Table(name = "clientes")
public class Usuario extends RepresentationModel<Cliente> {
  @Id
  @Schema(description = "ID do usuário", example = "1")
  private Long id;

  @Column(nullable = false)
  @Schema(description = "Nome do usuário", example = "João da Silva")
  private String nome;

  @Column
  @Schema(description = "Nome social do usuário", example = "João")
  private String nomeSocial;

  @Enumerated(EnumType.STRING)
  @Schema(description = "Perfil do usuário", example = "FUNCIONARIO")
  private PerfilUsuario perfil;

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
  @JoinColumn(name = "cliente_id")
  @Schema(description = "Lista de documentos do usuário")
  private List<Documento> documentos = new ArrayList<>();

  @OneToOne(optional = true, cascade = CascadeType.ALL)
  @JoinColumn(name = "endereco_id", nullable = true)
  private Endereco endereco;

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
  @JoinColumn(name = "cliente_id")
  @Schema(description = "Lista de telefones do usuário")
  private List<Telefone> telefones = new ArrayList<>();

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "cliente_id")
  @Schema(description = "Lista de emails do usuário")
  private List<Email> emails = new ArrayList<>();

  @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @Schema(description = "Credencial de usuário e senha")
  private CredencialUsuarioSenha credencialUsuarioSenha;

  @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @Schema(description = "Credencial de código de barras")
  private CredencialCodigoBarra credencialCodigoBarra;

  @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
  @JoinColumn(name = "cliente_id")
  @JsonIgnore
  @Schema(description = "Lista de mercadorias do usuário")
  private List<Mercadoria> mercadorias = new ArrayList<>();

  @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
  @JoinColumn(name = "cliente_id")
  @Schema(description = "Lista de veículos do usuário")
  private List<Veiculo> veiculos = new ArrayList<>();

  @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
  @JoinColumn(name = "cliente_id")
  @Schema(description = "Lista de vendas do usuário")
  private List<Venda> vendas = new ArrayList<>();

}