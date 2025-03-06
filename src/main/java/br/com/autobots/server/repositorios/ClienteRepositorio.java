package br.com.autobots.server.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autobots.server.entidades.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {

}
