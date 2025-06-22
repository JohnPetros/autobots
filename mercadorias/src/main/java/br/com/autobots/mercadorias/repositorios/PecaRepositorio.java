package br.com.autobots.mercadorias.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autobots.mercadorias.entidades.Peca;

public interface PecaRepositorio extends JpaRepository<Peca, Long> {
  List<Peca> findByEmpresaId(Long empresaId);
}
