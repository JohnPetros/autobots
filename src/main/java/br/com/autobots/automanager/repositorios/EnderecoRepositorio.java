package br.com.autobots.automanager.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autobots.automanager.entidades.Endereco;

public interface EnderecoRepositorio extends JpaRepository<Endereco, Long> {

}
