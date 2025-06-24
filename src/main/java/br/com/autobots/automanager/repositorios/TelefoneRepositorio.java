package br.com.autobots.automanager.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autobots.automanager.entidades.Telefone;

public interface TelefoneRepositorio extends JpaRepository<Telefone, Long> {
  Optional<Telefone> findByDddAndNumero(String ddd, String numero);
}
