package br.com.autobots.usuarios.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autobots.usuarios.entidades.Endereco;

public interface EnderecoRepositorio extends JpaRepository<Endereco, Long> {

}
