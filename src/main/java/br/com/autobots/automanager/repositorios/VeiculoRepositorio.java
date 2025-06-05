package br.com.autobots.automanager.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autobots.automanager.entidades.Veiculo;

public interface VeiculoRepositorio extends JpaRepository<Veiculo, Long> {

}
