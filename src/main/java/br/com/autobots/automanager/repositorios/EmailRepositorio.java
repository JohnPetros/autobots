package br.com.autobots.automanager.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autobots.automanager.entidades.Email;

public interface EmailRepositorio extends JpaRepository<Email, Long> {
  Optional<Email> findByEndereco(String endereco);
}
