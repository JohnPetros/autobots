package br.com.autobots.vendas.apis;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import br.com.autobots.vendas.entidades.Veiculo;

@FeignClient(name = "veiculos", url = "http://localhost:8083")
public interface VeiculosApi {
  @GetMapping("/{empresaId}/veiculo/{id}")
  public Veiculo obterVeiculo(
      @RequestHeader("Authorization") String authorization,
      @PathVariable Long empresaId,
      @PathVariable Long id);
}
