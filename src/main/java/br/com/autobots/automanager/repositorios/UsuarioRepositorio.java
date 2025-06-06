package br.com.autobots.automanager.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autobots.automanager.entidades.Usuario;
import br.com.autobots.automanager.entidades.Endereco;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
  Optional<Usuario> findByEndereco(Endereco endereco);
}
