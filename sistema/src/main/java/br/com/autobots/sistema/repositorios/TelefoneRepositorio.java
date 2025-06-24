package br.com.autobots.sistema.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autobots.sistema.entidades.Telefone;

public interface TelefoneRepositorio extends JpaRepository<Telefone, Long> {
  Optional<Telefone> findByDddAndNumero(String ddd, String numero);
}
