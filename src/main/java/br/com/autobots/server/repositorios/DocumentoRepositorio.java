package br.com.autobots.server.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autobots.server.entidades.Documento;

public interface DocumentoRepositorio extends JpaRepository<Documento, Long> {

}
