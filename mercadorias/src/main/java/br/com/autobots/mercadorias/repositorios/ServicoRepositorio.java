package br.com.autobots.mercadorias.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autobots.mercadorias.entidades.Servico;

public interface ServicoRepositorio extends JpaRepository<Servico, Long> {
  List<Servico> findByEmpresaId(Long empresaId);
}
