package br.com.autobots.server.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autobots.server.entidades.Telefone;

public interface TelefoneRepositorio extends JpaRepository<Telefone, Long> {

}
