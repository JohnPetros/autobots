package br.com.autobots.veiculos.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autobots.veiculos.entidades.Veiculo;

public interface VeiculoRepositorio extends JpaRepository<Veiculo, Long> {
  Optional<Veiculo> findByPlaca(String placa);

  List<Veiculo> findByEmpresaId(Long empresaId);
}
