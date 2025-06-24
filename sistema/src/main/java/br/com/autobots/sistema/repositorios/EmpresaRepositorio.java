package br.com.autobots.sistema.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autobots.sistema.entidades.Empresa;
import br.com.autobots.sistema.entidades.Endereco;

public interface EmpresaRepositorio extends JpaRepository<Empresa, Long> {
  Optional<Empresa> findByEndereco(Endereco endereco);

  Optional<Empresa> findByRazaoSocial(String nome);
}
