package br.com.autobots.sistema.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autobots.sistema.entidades.Veiculo;

public interface VeiculoRepositorio extends JpaRepository<Veiculo, Long> {
  Optional<Veiculo> findByPlaca(String placa);

  Optional<Veiculo> findByIdAndEmpresaId(Long id, Long empresaId);

  Optional<Veiculo> findByPlacaAndEmpresaId(String placa, Long empresaId);

  List<Veiculo> findAllByEmpresaId(Long empresaId);
}
