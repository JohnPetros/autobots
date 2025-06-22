package br.com.autobots.usuarios.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autobots.usuarios.entidades.Telefone;

public interface TelefoneRepositorio extends JpaRepository<Telefone, Long> {
  Optional<Telefone> findByDddAndNumero(String ddd, String numero);
}
