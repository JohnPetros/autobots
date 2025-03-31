package br.com.autobots.automanager.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autobots.automanager.entidades.Servico;

public interface ServicoRepositorio extends JpaRepository<Servico, Long> {

}
