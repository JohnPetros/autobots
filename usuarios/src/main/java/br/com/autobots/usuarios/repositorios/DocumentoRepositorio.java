package br.com.autobots.usuarios.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autobots.usuarios.entidades.Documento;

public interface DocumentoRepositorio extends JpaRepository<Documento, Long> {
  Optional<Documento> findByNumero(String numero);
}
