package br.com.autobots.automanager.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autobots.automanager.entidades.Mercadoria;

public interface MercadoriaRepositorio extends JpaRepository<Mercadoria, Long> {

}
