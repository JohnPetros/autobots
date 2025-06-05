package br.com.autobots.automanager.entidades;

import java.util.ArrayList;
import java.util.List;

import br.com.autobots.automanager.enums.PerfilUsuario;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = { "mercadorias", "vendas", "veiculos" })
@Entity
public class Usuario {
  @Id
  @Schema(description = "ID do usuário", example = "1")
  private Long id;

  @Column(nullable = false)
  @Schema(description = "Nome do usuário", example = "João da Silva")
  private String nome;

  @Column
  @Schema(description = "Nome social do usuário", example = "João")
  private String nomeSocial;

  @ElementCollection(fetch = FetchType.EAGER)
  @Schema(description = "Perfis do usuário", example = "ADMIN, USER")
  private List<PerfilUsuario> perfis = new ArrayList<>();

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @Schema(description = "Telefones do usuário", example = "11999999999")
  private List<Telefone> telefones = new ArrayList<>();

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @Schema(description = "Endereço do usuário", example = "Rua das Flores, 123")
  private Endereco endereco;

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @Schema(description = "Documentos do usuário", example = "CPF, RG")
  private List<Documento> documentos = new ArrayList<>();

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Email> emails = new ArrayList<>();

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Credencial> credenciais = new ArrayList<>();

  @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
  private List<Mercadoria> mercadorias = new ArrayList<>();

  @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
  private List<Venda> vendas = new ArrayList<>();

  @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
  private List<Veiculo> veiculos = new ArrayList<>();
}