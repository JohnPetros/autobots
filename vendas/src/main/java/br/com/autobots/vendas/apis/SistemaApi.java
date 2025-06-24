package br.com.autobots.vendas.apis;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import br.com.autobots.vendas.entidades.Peca;
import br.com.autobots.vendas.entidades.Servico;
import br.com.autobots.vendas.entidades.Usuario;
import br.com.autobots.vendas.entidades.Veiculo;

@FeignClient(name = "sistema", url = "http://localhost:8080")
public interface SistemaApi {
  @GetMapping("/usuario/email/{email}")
  public Usuario obterUsuarioPorEmail(@RequestHeader("Authorization") String authorization, @PathVariable String email);

  @GetMapping("/usuario/{id}")
  public Usuario obterUsuarioPorId(@RequestHeader("Authorization") String authorization, @PathVariable Long id);

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

  @GetMapping("/{empresaId}/veiculo/{veiculoId}")
  public Veiculo obterVeiculo(
      @RequestHeader("Authorization") String authorization,
      @PathVariable Long empresaId,
      @PathVariable Long veiculoId);
}
