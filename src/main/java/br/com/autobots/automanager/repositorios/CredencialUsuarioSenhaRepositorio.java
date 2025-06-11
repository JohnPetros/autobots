package br.com.autobots.automanager.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import br.com.autobots.automanager.entidades.CredencialUsuarioSenha;

public interface CredencialUsuarioSenhaRepositorio extends JpaRepository<CredencialUsuarioSenha, Long> {
  Optional<CredencialUsuarioSenha> findByNomeUsuario(String nomeUsuario);
}
