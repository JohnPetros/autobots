package br.com.autobots.sistema.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autobots.sistema.entidades.Peca;

public interface PecaRepositorio extends JpaRepository<Peca, Long> {
  List<Peca> findByEmpresaId(Long empresaId);
}
