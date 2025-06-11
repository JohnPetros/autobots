package br.com.autobots.automanager.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import br.com.autobots.automanager.entidades.Mercadoria;
import br.com.autobots.automanager.excecoes.NaoEncontradoExcecao;
import br.com.autobots.automanager.repositorios.MercadoriaRepositorio;
import br.com.autobots.automanager.servicos.AdicionaLinkMercadoriaServico;
import br.com.autobots.automanager.servicos.AtualizaMercadoriaServico;

@RestController
@Tag(name = "Mercadoria", description = "CRUD de mercadorias")
public class MercadoriaControlador {
  @Autowired
  private MercadoriaRepositorio repositorio;

  @Autowired
  private AdicionaLinkMercadoriaServico adicionaLinkMercadoriaServico;

  @Autowired
  private AtualizaMercadoriaServico atualizaMercadoriaServico;

  @PostMapping("/mercadoria/cadastrar")
  @Operation(summary = "Cadastrar telefone", description = "Cadastra um novo telefone")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Telefone cadastrado com sucesso"),
      @ApiResponse(responseCode = "409", description = "Telefone já cadastrado")
  })
  public ResponseEntity<?> cadastrarMercadoria(@RequestBody @Valid Mercadoria mercadoria) {
    repositorio.save(mercadoria);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping("/mercadorias")
  @Operation(summary = "Obter todos os mercadorias", description = "Retorna uma lista de todos os mercadorias cadastrados")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Mercadorias encontradas", content = @Content(schema = @Schema(implementation = List.class))),
      @ApiResponse(responseCode = "404", description = "Nenhum mercadoria cadastrado")
  })
  public ResponseEntity<List<Mercadoria>> obterMercadorias() {
    List<Mercadoria> mercadorias = repositorio.findAll();
    if (mercadorias.isEmpty()) {
      throw new NaoEncontradoExcecao("Nenhum mercadoria cadastrada");
    } else {
      adicionaLinkMercadoriaServico.adicionarLink(mercadorias);
      ResponseEntity<List<Mercadoria>> resposta = new ResponseEntity<>(mercadorias, HttpStatus.OK);
      return resposta;
    }
  }

  @GetMapping("/mercadoria/{id}")
  @Operation(summary = "Obter mercadoria", description = "Retorna um mercadoria específico com base no ID fornecido")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Mercadoria encontrada", content = @Content(schema = @Schema(implementation = Mercadoria.class))),
      @ApiResponse(responseCode = "404", description = "Mercadoria não encontrada")
  })
  public ResponseEntity<Mercadoria> obterMercadoria(@PathVariable long id) {
    Optional<Mercadoria> cliente = repositorio.findById(id);
    if (cliente.isEmpty()) {
      ResponseEntity<Mercadoria> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return resposta;
    } else {
      adicionaLinkMercadoriaServico.adicionarLink(cliente.get());
      return ResponseEntity.status(HttpStatus.OK).body(cliente.get());
    }
  }

  @PutMapping("/mercadoria/atualizar")
  @Operation(summary = "Atualizar mercadoria", description = "Atualiza as informações de um mercadoria existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Mercadoria atualizado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Mercadoria não encontrada")
  })
  public ResponseEntity<?> atualizarMercadoria(@RequestBody Mercadoria mercadoriaAtualizado) {
    Optional<Mercadoria> mercadoria = repositorio.findById(mercadoriaAtualizado.getId());
    if (mercadoria.isEmpty()) {
      throw new NaoEncontradoExcecao("Mercadoria não encontrada");
    }
    atualizaMercadoriaServico.atualizar(mercadoria.get(), mercadoriaAtualizado);
    repositorio.save(mercadoria.get());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/mercadoria/excluir")
  @Operation(summary = "Excluir mercadoria", description = "Exclui um mercadoria existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Mercadoria excluído com sucesso"),
      @ApiResponse(responseCode = "404", description = "Mercadoria não encontrada")
  })
  public ResponseEntity<?> excluirMercadoria(@RequestBody Mercadoria exclusao) {
    Optional<Mercadoria> mercadoria = repositorio.findById(exclusao.getId());
    if (mercadoria.isEmpty()) {
      throw new NaoEncontradoExcecao("Mercadoria não encontrada");
    }
    repositorio.delete(mercadoria.get());
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
