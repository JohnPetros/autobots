package br.com.autobots.sistema.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autobots.sistema.entidades.Endereco;

public interface EnderecoRepositorio extends JpaRepository<Endereco, Long> {

}
