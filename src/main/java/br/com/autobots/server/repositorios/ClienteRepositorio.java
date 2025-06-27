package br.com.autobots.server.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autobots.server.entidades.Cliente;
import br.com.autobots.server.entidades.Endereco;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
  Optional<Cliente> findByEndereco(Endereco endereco);
}
