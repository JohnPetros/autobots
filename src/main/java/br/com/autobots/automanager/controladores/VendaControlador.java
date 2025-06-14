package br.com.autobots.automanager.controladores;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import br.com.autobots.automanager.entidades.Empresa;
import br.com.autobots.automanager.entidades.Venda;
import br.com.autobots.automanager.excecoes.NaoEncontradoExcecao;
import br.com.autobots.automanager.provedores.AutenticacaoProvedor;
import br.com.autobots.automanager.repositorios.EmpresaRepositorio;
import br.com.autobots.automanager.repositorios.VendaRepositorio;
import br.com.autobots.automanager.servicos.AdicionaLinkVendaServico;
import br.com.autobots.automanager.servicos.AtualizaVendaServico;
import br.com.autobots.automanager.servicos.ObterVendaServico;
import br.com.autobots.automanager.servicos.ObterVendasServico;
import br.com.autobots.automanager.servicos.ValidaVendaServico;

@RestController
@Tag(name = "Venda", description = "CRUD de vendas")
public class VendaControlador {
  @Autowired
  private VendaRepositorio vendaRepositorio;

  @Autowired
  private EmpresaRepositorio empresaRepositorio;

  @Autowired
  public AutenticacaoProvedor autenticacaoProvedor;

  @Autowired
  private AdicionaLinkVendaServico adicionaLinkVendaServico;

  @Autowired
  private AtualizaVendaServico atualizaVendaServico;

  @Autowired
  private ValidaVendaServico validaVendaServico;

  @Autowired
  private ObterVendasServico obterVendasServico;

  @Autowired
  private ObterVendaServico obterVendaServico;

  private final Random random = new Random();

  @PreAuthorize("""
      hasRole('ADMIN') or
      hasRole('GERENTE') or
      (hasRole('VENDEDOR') and #venda.vendedor.id == authentication.principal.usuario.id)
      """)
  @PostMapping("/{empresaId}/venda/cadastrar")
  @Operation(summary = "Cadastrar venda", description = "Cadastra um novo venda")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Venda cadastrada com sucesso"),
      @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
      @ApiResponse(responseCode = "404", description = "Mercadoria não encontrada"),
      @ApiResponse(responseCode = "404", description = "Serviço não encontrado")
  })
  public ResponseEntity<?> cadastrarVenda(@RequestBody @Valid Venda venda, @PathVariable long empresaId) {
    Optional<Empresa> empresa = empresaRepositorio.findById(empresaId);
    if (empresa.isEmpty()) {
      throw new NaoEncontradoExcecao("Empresa não encontrada");
    }

    venda.setIdentificacao(gerarIdentificacaoAleatoria());
    venda.setEmpresa(empresa.get());
    validaVendaServico.validar(venda);
    empresa.get().getVendas().add(venda);
    empresaRepositorio.save(empresa.get());
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping("/{empresaId}/vendas")
  @Operation(summary = "Obter todos os vendas", description = "Retorna uma lista de todos os vendas cadastradas")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Vendas encontrados", content = @Content(schema = @Schema(implementation = List.class))),
      @ApiResponse(responseCode = "404", description = "Nenhum venda cadastrada"),
      @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
  })
  public ResponseEntity<List<Venda>> obterVendas(@PathVariable long empresaId) {
    var vendas = obterVendasServico.obterVendas(empresaId);
    adicionaLinkVendaServico.adicionarLink(vendas, empresaId);
    return new ResponseEntity<>(vendas, HttpStatus.OK);
  }

  @GetMapping("/{empresaId}/venda/{id}")
  @Operation(summary = "Obter venda", description = "Retorna um venda específico com base no ID fornecido")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Venda encontrado", content = @Content(schema = @Schema(implementation = Venda.class))),
      @ApiResponse(responseCode = "404", description = "Venda não encontrado"),
      @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
  })
  public ResponseEntity<Venda> obterVenda(@PathVariable long id, @PathVariable long empresaId) {
    var venda = obterVendaServico.obterVenda(id, empresaId);
    adicionaLinkVendaServico.adicionarLink(venda, empresaId);
    return new ResponseEntity<>(venda, HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
  @PutMapping("/{empresaId}/venda/atualizar")
  @Operation(summary = "Atualizar venda", description = "Atualiza as informações de um venda existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Venda atualizada com sucesso"),
      @ApiResponse(responseCode = "422", description = "Venda não pode ser atualizada"),
      @ApiResponse(responseCode = "404", description = "Venda não encontrada"),
      @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
  })
  public ResponseEntity<?> atualizarVenda(@RequestBody Venda vendaAtualizado, @PathVariable long empresaId) {
    Optional<Empresa> empresa = empresaRepositorio.findById(empresaId);
    if (empresa.isEmpty()) {
      throw new NaoEncontradoExcecao("Empresa não encontrada");
    }
    Optional<Venda> venda = vendaRepositorio.findById(vendaAtualizado.getId());
    if (venda.isEmpty()) {
      throw new NaoEncontradoExcecao("Venda não encontrada");
    }
    atualizaVendaServico.atualizar(venda.get(), vendaAtualizado);
    validaVendaServico.validar(venda.get());
    vendaRepositorio.save(venda.get());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
  @DeleteMapping("/{empresaId}/venda/excluir")
  @Operation(summary = "Excluir venda", description = "Exclui um venda existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Venda excluída com sucesso"),
      @ApiResponse(responseCode = "404", description = "Venda não encontrada"),
      @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
  })
  @Transactional
  public ResponseEntity<?> excluirVenda(@RequestBody Venda exclusao, @PathVariable long empresaId) {
    var empresa = empresaRepositorio.findById(empresaId);
    if (empresa.isEmpty()) {
      throw new NaoEncontradoExcecao("Empresa não encontrada");
    }
    var venda = vendaRepositorio.findById(exclusao.getId());
    if (venda.isEmpty()) {
      throw new NaoEncontradoExcecao("Venda não encontrada");
    }

    empresa.get().getVendas().remove(venda.get());
    empresaRepositorio.save(empresa.get());

    return new ResponseEntity<>(HttpStatus.OK);
  }

  private String gerarIdentificacaoAleatoria() {
    StringBuilder identificacao = new StringBuilder("V");
    for (int i = 0; i < 10; i++) {
      identificacao.append(random.nextInt(10));
    }
    return identificacao.toString();
  }
}