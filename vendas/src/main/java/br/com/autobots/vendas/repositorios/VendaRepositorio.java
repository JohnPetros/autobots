package br.com.autobots.vendas.repositorios;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autobots.vendas.entidades.Venda;

public interface VendaRepositorio extends JpaRepository<Venda, Long> {
  Optional<Venda> findByIdAndEmpresaId(Long id, Long empresaId);

  List<Venda> findByClienteId(Long clienteId);

  List<Venda> findByVendedorId(Long vendedorId);

  List<Venda> findAllByEmpresaId(Long empresaId);

  List<Venda> findByClienteIdAndCadastroBetween(Long clienteId, LocalDate dataInicio, LocalDate dataFim);

  List<Venda> findByVendedorIdAndCadastroBetween(Long vendedorId, LocalDate dataInicio, LocalDate dataFim);

  List<Venda> findAllByEmpresaIdAndCadastroBetween(Long empresaId, LocalDate dataInicio, LocalDate dataFim);
}
