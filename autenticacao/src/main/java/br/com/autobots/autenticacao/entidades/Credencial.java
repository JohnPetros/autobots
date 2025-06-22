package br.com.autobots.autenticacao.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Credencial {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  @NotBlank(message = "Email é obrigatório")
  private String email;

  @Column(nullable = false)
  @NotBlank(message = "Senha é obrigatória")
  private String senha;

  @Column(nullable = false)
  @NotNull(message = "ID do usuário é obrigatório")
  private Long usuarioId;
}