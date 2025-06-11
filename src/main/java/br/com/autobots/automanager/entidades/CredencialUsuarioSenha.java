package br.com.autobots.automanager.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class CredencialUsuarioSenha extends Credencial {
  @Column(nullable = false, unique = true)
  @NotBlank(message = "Nome de usuário é obrigatório")
  private String nomeUsuario;

  @Column(nullable = false)
  @NotBlank(message = "Senha é obrigatória")
  private String senha;
}
