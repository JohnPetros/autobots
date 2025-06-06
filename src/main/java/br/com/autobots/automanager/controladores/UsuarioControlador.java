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

import br.com.autobots.automanager.entidades.Usuario;
import br.com.autobots.automanager.servicos.AdicionaLinkUsuarioServico;
import br.com.autobots.automanager.servicos.AtualizaUsuarioServico;
import br.com.autobots.automanager.repositorios.UsuarioRepositorio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@Tag(name = "Usuarios", description = "CRUD de usuarios")
public class UsuarioControlador {
  @Autowired
  private UsuarioRepositorio repositorio;

  @Autowired
  private AdicionaLinkUsuarioServico adicionaLinkUsuarioServico;

  @Autowired
  private AtualizaUsuarioServico atualizaUsuarioServico;

  @GetMapping("/usuarios")
  @Operation(summary = "Obter todos os usuarios", description = "Retorna uma lista de todos os usuarios cadastrados")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Usuarios encontrados", content = @Content(schema = @Schema(implementation = List.class))),
      @ApiResponse(responseCode = "404", description = "Nenhum usuario cadastrado")
  })
  public ResponseEntity<List<Usuario>> obterUsuarios() {
    List<Usuario> usuarios = repositorio.findAll();
    if (usuarios.isEmpty()) {
      ResponseEntity<List<Usuario>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return resposta;
    } else {
      adicionaLinkUsuarioServico.adicionarLink(usuarios);
      ResponseEntity<List<Usuario>> resposta = new ResponseEntity<>(usuarios, HttpStatus.OK);
      return resposta;
    }
  }

  @GetMapping("/usuario/{id}")
  @Operation(summary = "Obter usuario", description = "Retorna um usuario específico com base no ID fornecido")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Usuario encontrado", content = @Content(schema = @Schema(implementation = Usuario.class))),
      @ApiResponse(responseCode = "404", description = "Usuario não encontrado")
  })
  public ResponseEntity<Usuario> obterUsuario(@PathVariable long id) {
    Optional<Usuario> usuario = repositorio.findById(id);
    if (usuario.isEmpty()) {
      ResponseEntity<Usuario> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return resposta;
    } else {
      adicionaLinkUsuarioServico.adicionarLink(usuario.get());
      return ResponseEntity.status(HttpStatus.OK).body(usuario.get());
    }
  }

  @PostMapping("/usuario/cadastrar")
  @Operation(summary = "Cadastrar usuario", description = "Cadastra um novo usuario")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Usuario cadastrado com sucesso"),
      @ApiResponse(responseCode = "409", description = "Usuario já cadastrado")
  })
  public ResponseEntity<?> cadastrarUsuario(@RequestBody Usuario usuario) {
    HttpStatus status = HttpStatus.CONFLICT;
    Optional<Usuario> usuarioExistente = repositorio.findById(usuario.getId());
    if (usuarioExistente.isEmpty()) {
      repositorio.save(usuario);
      status = HttpStatus.CREATED;
    }
    return new ResponseEntity<>(status);
  }

  @PutMapping("/usuario/atualizar")
  @Operation(summary = "Atualizar usuario", description = "Atualiza as informações de um usuario existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Usuario atualizado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Usuario não encontrado")
  })
  public ResponseEntity<?> atualizarUsuario(@RequestBody Usuario usuarioAtualizacao) {
    Optional<Usuario> usuario = repositorio.findById(usuarioAtualizacao.getId());
    if (usuario.isPresent()) {
      atualizaUsuarioServico.atualizar(usuario.get(), usuarioAtualizacao);
      repositorio.save(usuario.get());
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("/usuario/excluir")
  @Operation(summary = "Excluir usuario", description = "Exclui um usuario existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Usuario excluído com sucesso"),
      @ApiResponse(responseCode = "404", description = "Usuario não encontrado")
  })
  public ResponseEntity<?> excluirUsuario(@RequestBody Usuario exclusao) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    Optional<Usuario> usuario = repositorio.findById(exclusao.getId());
    if (usuario.isPresent()) {
      repositorio.delete(usuario.get());
      status = HttpStatus.OK;
    }
    return new ResponseEntity<>(status);
  }

}