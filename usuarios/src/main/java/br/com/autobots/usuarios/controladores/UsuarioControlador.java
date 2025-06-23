package br.com.autobots.usuarios.controladores;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.autobots.usuarios.apis.AutenticacaoApi;
import br.com.autobots.usuarios.entidades.Usuario;
import br.com.autobots.usuarios.repositorios.UsuarioRepositorio;
import br.com.autobots.usuarios.servicos.AdicionaLinkUsuarioServico;
import br.com.autobots.usuarios.servicos.AtualizaUsuarioServico;
import br.com.autobots.usuarios.servicos.ExcluirUsuarioServico;
import br.com.autobots.usuarios.servicos.ObterUsuarioServico;
import br.com.autobots.usuarios.servicos.ObterUsuariosServico;
import br.com.autobots.usuarios.servicos.ValidaUsuarioServico;
import jakarta.validation.Valid;

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
  private UsuarioRepositorio usuarioRepositorio;

  @Autowired
  private AutenticacaoApi autenticacaoApi;

  @Autowired
  private ValidaUsuarioServico validaUsuarioServico;

  @Autowired
  private ObterUsuarioServico obterUsuarioServico;

  @Autowired
  private ObterUsuariosServico obterUsuariosServico;

  @Autowired
  private AdicionaLinkUsuarioServico adicionaLinkUsuarioServico;

  @Autowired
  private AtualizaUsuarioServico atualizaUsuarioServico;

  @Autowired
  private ExcluirUsuarioServico excluirUsuarioServico;

  @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
  @GetMapping("/{empresaId}/clientes")
  @Operation(summary = "Obter todos os usuarios", description = "Retorna uma lista de todos os usuarios cadastrados")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Usuarios encontrados", content = @Content(schema = @Schema(implementation = List.class))),
      @ApiResponse(responseCode = "404", description = "Nenhum usuario cadastrado"),
  })
  public ResponseEntity<List<Usuario>> obterClientes(@PathVariable long empresaId) {
    var usuarios = obterUsuariosServico.obterUsuarios(empresaId, "clientes");
    adicionaLinkUsuarioServico.adicionarLink(usuarios, empresaId);
    return ResponseEntity.status(HttpStatus.OK).body(usuarios);
  }

  @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
  @GetMapping("/{empresaId}/funcionarios")
  @Operation(summary = "Obter todos os usuarios", description = "Retorna uma lista de todos os usuarios cadastrados")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Usuarios encontrados", content = @Content(schema = @Schema(implementation = List.class))),
      @ApiResponse(responseCode = "404", description = "Nenhum usuario cadastrado"),
  })
  public ResponseEntity<List<Usuario>> obterFuncionarios(@PathVariable long empresaId) {
    var usuarios = obterUsuariosServico.obterUsuarios(empresaId, "funcionarios");
    adicionaLinkUsuarioServico.adicionarLink(usuarios, empresaId);
    return ResponseEntity.status(HttpStatus.OK).body(usuarios);
  }

  @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
  @GetMapping("/{empresaId}/usuarios")
  @Operation(summary = "Obter todos os usuarios", description = "Retorna uma lista de todos os usuarios cadastrados")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Usuarios encontrados", content = @Content(schema = @Schema(implementation = List.class))),
      @ApiResponse(responseCode = "404", description = "Nenhum usuario cadastrado"),
  })
  public ResponseEntity<List<Usuario>> obterUsuarios(@PathVariable long empresaId) {
    var usuarios = obterUsuariosServico.obterUsuarios(empresaId, "todos");
    adicionaLinkUsuarioServico.adicionarLink(usuarios, empresaId);
    return ResponseEntity.status(HttpStatus.OK).body(usuarios);
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR', 'CLIENTE')")
  @GetMapping("/usuario/{id}")
  @Operation(summary = "Obter usuario", description = "Retorna um usuario específico com base no ID fornecido")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Usuario encontrado", content = @Content(schema = @Schema(implementation = Usuario.class))),
      @ApiResponse(responseCode = "404", description = "Usuario não encontrado"),
  })
  public ResponseEntity<Usuario> obterUsuario(@PathVariable Long id) {
    var usuario = obterUsuarioServico.obterUsuario(id);
    adicionaLinkUsuarioServico.adicionarLink(usuario, usuario.getEmpresaId());
    return ResponseEntity.status(HttpStatus.OK).body(usuario);
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR', 'CLIENTE')")
  @GetMapping("/usuario/email/{email}")
  @Operation(summary = "Obter usuario", description = "Retorna um usuario específico com base no email fornecido")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Usuario encontrado", content = @Content(schema = @Schema(implementation = Usuario.class))),
      @ApiResponse(responseCode = "404", description = "Usuario não encontrado"),
  })
  public ResponseEntity<Usuario> obterUsuarioPorEmail(@PathVariable String email) {
    var usuario = obterUsuarioServico.obterUsuarioPorEmail(email);
    adicionaLinkUsuarioServico.adicionarLink(usuario, usuario.getEmpresaId());
    return ResponseEntity.status(HttpStatus.OK).body(usuario);
  }

  @PreAuthorize("""
      hasRole('ADMIN') or
      (hasRole('GERENTE') and #usuario.perfil.name() != 'ADMIN') or
      (hasRole('VENDEDOR') and #usuario.perfil.name() == 'CLIENTE')
      """)
  @PostMapping("/{empresaId}/usuario/cadastrar")
  @Operation(summary = "Cadastrar usuario", description = "Cadastra um novo usuario")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Usuario cadastrado com sucesso"),
      @ApiResponse(responseCode = "409", description = "Usuario já cadastrado com esse nome de usuário"),
      @ApiResponse(responseCode = "409", description = "Usuario já cadastrado com essa código de barras"),
      @ApiResponse(responseCode = "409", description = "Documento já cadastrado"),
      @ApiResponse(responseCode = "409", description = "Usuario já cadastrado")
  })
  public ResponseEntity<?> cadastrarUsuario(@RequestBody @Valid Usuario usuario, @PathVariable long empresaId) {
    usuario.setEmpresaId(empresaId);
    validaUsuarioServico.validar(usuario);
    autenticacaoApi.registrar(usuario.getEmail(), usuario.getSenha());
    usuarioRepositorio.save(usuario);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PreAuthorize("""
      hasRole('ADMIN') or
      (hasRole('GERENTE') and #usuarioAtualizacao.perfil.name() != 'ADMIN') or
      (hasRole('VENDEDOR') and #usuarioAtualizacao.perfil.name() == 'CLIENTE')
      """)
  @PutMapping("/{empresaId}/usuario/atualizar")
  @Operation(summary = "Atualizar usuario", description = "Atualiza as informações de um usuario existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Usuario cadastrado com sucesso"),
      @ApiResponse(responseCode = "409", description = "Documento já cadastrado"),
      @ApiResponse(responseCode = "409", description = "Usuario já cadastrado")
  })
  public ResponseEntity<?> atualizarUsuario(@RequestBody Usuario usuarioAtualizacao) {
    atualizaUsuarioServico.atualizar(usuarioAtualizacao);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
  @DeleteMapping("/{empresaId}/usuario/excluir")
  @Operation(summary = "Excluir usuario", description = "Exclui um usuario existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Usuario excluído com sucesso"),
      @ApiResponse(responseCode = "404", description = "Usuario não encontrado"),
  })
  public ResponseEntity<?> excluirUsuario(@RequestBody Usuario exclusao) {
    excluirUsuarioServico.excluir(exclusao);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}