package br.com.autobots.sistema.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autobots.sistema.entidades.Documento;

public interface DocumentoRepositorio extends JpaRepository<Documento, Long> {
  Optional<Documento> findByNumero(String numero);
}
