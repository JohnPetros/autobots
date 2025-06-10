package br.com.autobots.automanager.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autobots.automanager.entidades.Empresa;
import br.com.autobots.automanager.entidades.Endereco;

public interface EmpresaRepositorio extends JpaRepository<Empresa, Long> {
  Optional<Empresa> findByEndereco(Endereco endereco);

  Optional<Empresa> findByRazaoSocial(String nome);
}
