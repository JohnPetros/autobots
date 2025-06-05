package br.com.autobots.automanager.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autobots.automanager.entidades.Empresa;

public interface RepositorioEmpresa extends JpaRepository<Empresa, Long> {
  Optional<Empresa> findByRazaoSocial(String nome);
}
