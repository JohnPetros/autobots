package br.com.autobots.automanager.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autobots.automanager.entidades.Telefone;

public interface TelefoneRepositorio extends JpaRepository<Telefone, Long> {

}
