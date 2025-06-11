package br.com.autobots.automanager.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import br.com.autobots.automanager.entidades.CredencialCodigoBarra;

public interface CredencialCodigoBarraRepositorio extends JpaRepository<CredencialCodigoBarra, Long> {
  Optional<CredencialCodigoBarra> findByCodigo(long codigo);
}
