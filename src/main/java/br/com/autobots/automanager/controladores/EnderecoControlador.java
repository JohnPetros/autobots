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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.autobots.automanager.entidades.Endereco;
import br.com.autobots.automanager.repositorios.EnderecoRepositorio;
import br.com.autobots.automanager.servicos.AdicionaLinkEnderecoServico;
import br.com.autobots.automanager.servicos.AtualizaEnderecoServico;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("enderecos")
@Tag(name = "Endereço", description = "CRUD de endereços")
public class EnderecoControlador {
  @Autowired
  private EnderecoRepositorio repositorio;

  @Autowired
  private AtualizaEnderecoServico atualizaEnderecoServico;

  @Autowired
  private AdicionaLinkEnderecoServico adicionaLinkEnderecoServico;

  @PostMapping
  @Operation(summary = "Cadastrar endereço", description = "Cadastra um novo endereço")
  public ResponseEntity<?> cadastrarEndereco(@RequestBody Endereco endereco) {
    Optional<Endereco> enderecoExistente = repositorio.findById(endereco.getId());
    if (enderecoExistente.isEmpty()) {
      repositorio.save(endereco);
      return new ResponseEntity<>(HttpStatus.CREATED);
    }
    return new ResponseEntity<>(HttpStatus.CONFLICT);
  }

  @GetMapping
  @Operation(summary = "Obter todos os endereços", description = "Retorna uma lista de todos os endereços cadastrados")
  public ResponseEntity<List<Endereco>> obterEnderecos() {
    List<Endereco> enderecos = repositorio.findAll();
    if (enderecos.isEmpty()) {
      ResponseEntity<List<Endereco>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return resposta;
    } else {
      adicionaLinkEnderecoServico.adicionarLink(enderecos);
      ResponseEntity<List<Endereco>> resposta = new ResponseEntity<>(enderecos, HttpStatus.OK);
      return resposta;
    }
  }

  @GetMapping("/{id}")
  @Operation(summary = "Obter endereço", description = "Retorna um endereço específico com base no ID fornecido")
  public ResponseEntity<Endereco> obterEndereco(@PathVariable long id) {
    Optional<Endereco> cliente = repositorio.findById(id);
    if (cliente.isEmpty()) {
      ResponseEntity<Endereco> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return resposta;
    } else {
      adicionaLinkEnderecoServico.adicionarLink(cliente.get());
      return ResponseEntity.status(HttpStatus.OK).body(cliente.get());
    }
  }

  @PutMapping
  @Operation(summary = "Atualizar endereço", description = "Atualiza as informações de um endereço existente")
  public void atualizarEndereco(@RequestBody Endereco EnderecoAtualizado) {
    var endereco = repositorio.findById(EnderecoAtualizado.getId());
    atualizaEnderecoServico.atualizar(endereco.get(), EnderecoAtualizado);
    repositorio.save(endereco.get());
  }

  @DeleteMapping
  @Operation(summary = "Excluir endereço", description = "Exclui um endereço existente")
  public void excluirEndereco(@RequestBody Endereco exclusao) {
    var endereco = repositorio.findById(exclusao.getId());
    repositorio.delete(endereco.get());
  }
}
