package br.com.autobots.server.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autobots.server.entidades.Endereco;

public interface EnderecoRepositorio extends JpaRepository<Endereco, Long> {

}
