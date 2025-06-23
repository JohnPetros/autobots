package br.com.autobots.vendas.apis;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import br.com.autobots.vendas.entidades.Peca;
import br.com.autobots.vendas.entidades.Servico;

@FeignClient(name = "mercadorias", url = "http://localhost:8084")
public interface MercadoriasApi {
  @GetMapping("/{empresaId}/peca/{pecaId}")
  public Peca obterPeca(
      @RequestHeader("Authorization") String authorization,
      @PathVariable Long empresaId,
      @PathVariable Long pecaId);

  @GetMapping("/{empresaId}/servico/{servicoId}")
  public Servico obterServico(
      @RequestHeader("Authorization") String authorization,
      @PathVariable Long empresaId,
      @PathVariable Long servicoId);
}
