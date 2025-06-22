package br.com.autobots.autenticacao.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import br.com.autobots.autenticacao.entidades.Credencial;

public interface CredencialRepositorio extends JpaRepository<Credencial, Long> {
  Optional<Credencial> findByEmail(String email);
}
