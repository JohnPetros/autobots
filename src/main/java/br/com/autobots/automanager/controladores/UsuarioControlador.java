package br.com.autobots.automanager.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.autobots.automanager.entidades.Usuario;
import br.com.autobots.automanager.excecoes.IdVazioExcecao;
import br.com.autobots.automanager.excecoes.NaoEncontradoExcecao;
import br.com.autobots.automanager.servicos.AdicionaLinkUsuarioServico;
import br.com.autobots.automanager.servicos.AtualizaUsuarioServico;
import br.com.autobots.automanager.servicos.ValidaUsuarioServico;
import br.com.autobots.automanager.repositorios.EmpresaRepositorio;
import br.com.autobots.automanager.repositorios.UsuarioRepositorio;
import br.com.autobots.automanager.entidades.Empresa;
import br.com.autobots.automanager.repositorios.CredencialCodigoBarraRepositorio;
import br.com.autobots.automanager.repositorios.CredencialUsuarioSenhaRepositorio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@Tag(name = "Usuarios", description = "CRUD de usuarios")
public class UsuarioControlador {
  @Autowired
  private UsuarioRepositorio usuarioRepositorio;

  @Autowired
  private EmpresaRepositorio empresaRepositorio;

  @Autowired
  private CredencialUsuarioSenhaRepositorio credencialUsuarioSenhaRepositorio;

  @Autowired
  private CredencialCodigoBarraRepositorio credencialCodigoBarraRepositorio;

  @Autowired
  private ValidaUsuarioServico validaUsuarioServico;

  @Autowired
  private AdicionaLinkUsuarioServico adicionaLinkUsuarioServico;

  @Autowired
  private AtualizaUsuarioServico atualizaUsuarioServico;

  @GetMapping("/{empresaId}/usuarios")
  @Operation(summary = "Obter todos os usuarios", description = "Retorna uma lista de todos os usuarios cadastrados")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Usuarios encontrados", content = @Content(schema = @Schema(implementation = List.class))),
      @ApiResponse(responseCode = "404", description = "Nenhum usuario cadastrado"),
      @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
  })
  public ResponseEntity<List<Usuario>> obterUsuarios(@PathVariable long empresaId) {
    Optional<Empresa> empresa = empresaRepositorio.findById(empresaId);
    if (empresa.isEmpty()) {
      throw new NaoEncontradoExcecao("Empresa não encontrada");
    }
    List<Usuario> usuarios = empresa.get().getUsuarios();
    if (usuarios.isEmpty()) {
      throw new NaoEncontradoExcecao("Nenhum usuario cadastrado");
    } else {
      adicionaLinkUsuarioServico.adicionarLink(usuarios, empresaId);
      ResponseEntity<List<Usuario>> resposta = new ResponseEntity<>(usuarios, HttpStatus.OK);
      return resposta;
    }
  }

  @GetMapping("/{empresaId}/usuario/{id}")
  @Operation(summary = "Obter usuario", description = "Retorna um usuario específico com base no ID fornecido")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Usuario encontrado", content = @Content(schema = @Schema(implementation = Usuario.class))),
      @ApiResponse(responseCode = "404", description = "Usuario não encontrado"),
      @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
  })
  public ResponseEntity<Usuario> obterUsuario(@PathVariable long id, @PathVariable long empresaId) {
    Optional<Usuario> usuario = usuarioRepositorio.findById(id);
    if (usuario.isEmpty()) {
      throw new NaoEncontradoExcecao("Usuário não encontrado");
    } else {
      adicionaLinkUsuarioServico.adicionarLink(usuario.get(), empresaId);
      return ResponseEntity.status(HttpStatus.OK).body(usuario.get());
    }
  }

  @PostMapping("/{empresaId}/usuario/cadastrar")
  @Operation(summary = "Cadastrar usuario", description = "Cadastra um novo usuario")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Usuario cadastrado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Empresa não encontrada"),
      @ApiResponse(responseCode = "409", description = "Usuario já cadastrado com esse nome de usuário"),
      @ApiResponse(responseCode = "409", description = "Usuario já cadastrado com essa código de barras"),
      @ApiResponse(responseCode = "409", description = "Documento já cadastrado"),
      @ApiResponse(responseCode = "409", description = "Usuario já cadastrado")
  })
  public ResponseEntity<?> cadastrarUsuario(@RequestBody @Valid Usuario usuario, @PathVariable long empresaId) {
    Optional<Empresa> empresa = empresaRepositorio.findById(empresaId);
    if (empresa.isEmpty()) {
      throw new NaoEncontradoExcecao("Empresa não encontrada");
    }
    validaUsuarioServico.validar(usuario);
    usuarioRepositorio.save(usuario);
    empresa.get().getUsuarios().add(usuario);
    empresaRepositorio.save(empresa.get());
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PutMapping("/{empresaId}/usuario/atualizar")
  @Operation(summary = "Atualizar usuario", description = "Atualiza as informações de um usuario existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Usuario cadastrado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Empresa não encontrada"),
      @ApiResponse(responseCode = "409", description = "Usuario já cadastrado com esse nome de usuário"),
      @ApiResponse(responseCode = "409", description = "Usuario já cadastrado com essa código de barras"),
      @ApiResponse(responseCode = "409", description = "Documento já cadastrado"),
      @ApiResponse(responseCode = "409", description = "Usuario já cadastrado")
  })
  public ResponseEntity<?> atualizarUsuario(@RequestBody Usuario usuarioAtualizacao, @PathVariable long empresaId) {
    if (usuarioAtualizacao.getId() == null) {
      throw new IdVazioExcecao();
    }
    Optional<Empresa> empresa = empresaRepositorio.findById(empresaId);
    if (empresa.isEmpty()) {
      throw new NaoEncontradoExcecao("Empresa não encontrada");
    }
    Optional<Usuario> usuario = usuarioRepositorio.findById(usuarioAtualizacao.getId());
    if (usuario.isEmpty()) {
      throw new NaoEncontradoExcecao("Usuario não encontrado");
    }
    validaUsuarioServico.validar(usuarioAtualizacao);
    atualizaUsuarioServico.atualizar(usuario.get(), usuarioAtualizacao);
    System.out.println(usuario.get().getEmails());
    usuarioRepositorio.save(usuario.get());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/{empresaId}/usuario/excluir")
  @Operation(summary = "Excluir usuario", description = "Exclui um usuario existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Usuario excluído com sucesso"),
      @ApiResponse(responseCode = "404", description = "Usuario não encontrado"),
      @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
  })
  public ResponseEntity<?> excluirUsuario(@RequestBody Usuario exclusao, @PathVariable long empresaId) {
    if (exclusao.getId() == null) {
      throw new IdVazioExcecao();
    }
    Optional<Empresa> empresa = empresaRepositorio.findById(empresaId);
    if (empresa.isEmpty()) {
      throw new NaoEncontradoExcecao("Empresa não encontrada");
    }
    Optional<Usuario> usuario = usuarioRepositorio.findById(exclusao.getId());
    if (usuario.isEmpty()) {
      throw new NaoEncontradoExcecao("Usuario não encontrado");
    }

    usuarioRepositorio.delete(usuario.get());
    credencialUsuarioSenhaRepositorio.delete(usuario.get().getCredencialUsuarioSenha());
    credencialCodigoBarraRepositorio.delete(usuario.get().getCredencialCodigoBarra());
    empresa.get().getUsuarios().remove(usuario.get());
    empresaRepositorio.save(empresa.get());
    return new ResponseEntity<>(HttpStatus.OK);
  }

}