package br.com.autobots.automanager.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autobots.automanager.entidades.Cliente;
import br.com.autobots.automanager.entidades.Endereco;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
  Optional<Cliente> findByEndereco(Endereco endereco);
}
