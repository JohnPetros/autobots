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

import br.com.autobots.automanager.entidades.Telefone;
import br.com.autobots.automanager.repositorios.TelefoneRepositorio;
import br.com.autobots.automanager.servicos.AdicionaLinkTelefoneServico;
import br.com.autobots.automanager.servicos.AtualizaTelefoneServico;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("telefones")
@Tag(name = "Telefone", description = "CRUD de telefones")
public class TelefoneControlador {
  @Autowired
  private TelefoneRepositorio repositorio;

  @Autowired
  private AtualizaTelefoneServico atualizaTelefoneServico;

  @Autowired
  private AdicionaLinkTelefoneServico adicionaLinkTelefoneServico;

  @PostMapping
  @Operation(summary = "Cadastrar telefone", description = "Cadastra um novo telefone")
  public ResponseEntity<?> cadastrarTelefone(@RequestBody Telefone telefone) {
    Optional<Telefone> telefoneExistente = repositorio.findById(telefone.getId());
    if (telefoneExistente.isEmpty()) {
      repositorio.save(telefone);
      return new ResponseEntity<>(HttpStatus.CREATED);
    }
    return new ResponseEntity<>(HttpStatus.CONFLICT);
  }

  @GetMapping
  @Operation(summary = "Obter todos os telefones", description = "Retorna uma lista de todos os telefones cadastrados")
  public ResponseEntity<List<Telefone>> obterTelefones() {
    List<Telefone> telefones = repositorio.findAll();
    if (telefones.isEmpty()) {
      ResponseEntity<List<Telefone>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return resposta;
    } else {
      adicionaLinkTelefoneServico.adicionarLink(telefones);
      ResponseEntity<List<Telefone>> resposta = new ResponseEntity<>(telefones, HttpStatus.OK);
      return resposta;
    }
  }

  @GetMapping("/{id}")
  @Operation(summary = "Obter telefone", description = "Retorna um telefone específico com base no ID fornecido")
  public ResponseEntity<Telefone> obterTelefone(@PathVariable long id) {
    Optional<Telefone> cliente = repositorio.findById(id);
    if (cliente.isEmpty()) {
      ResponseEntity<Telefone> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return resposta;
    } else {
      adicionaLinkTelefoneServico.adicionarLink(cliente.get());
      return ResponseEntity.status(HttpStatus.OK).body(cliente.get());
    }
  }

  @PutMapping
  @Operation(summary = "Atualizar telefone", description = "Atualiza as informações de um telefone existente")
  public void atualizarTelefone(@RequestBody Telefone TelefoneAtualizado) {
    var telefone = repositorio.findById(TelefoneAtualizado.getId());
    atualizaTelefoneServico.atualizar(telefone.get(), TelefoneAtualizado);
    repositorio.save(telefone.get());
  }

  @DeleteMapping
  @Operation(summary = "Excluir telefone", description = "Exclui um telefone existente")
  public void excluirTelefone(@RequestBody Telefone exclusao) {
    var telefone = repositorio.findById(exclusao.getId());
    repositorio.delete(telefone.get());
  }
}
