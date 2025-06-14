package br.com.autobots.automanager.controladores;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RestController;

import br.com.autobots.automanager.entidades.Usuario;
import br.com.autobots.automanager.excecoes.NaoEncontradoExcecao;
import br.com.autobots.automanager.provedores.AutenticacaoProvedor;
import br.com.autobots.automanager.servicos.AdicionaLinkUsuarioServico;
import br.com.autobots.automanager.servicos.AtualizaUsuarioServico;
import br.com.autobots.automanager.servicos.ExcluirUsuarioServico;
import br.com.autobots.automanager.servicos.ObterUsuariosServico;
import br.com.autobots.automanager.servicos.ObterUsuarioServico;
import br.com.autobots.automanager.servicos.ValidaUsuarioServico;
import br.com.autobots.automanager.repositorios.CredencialRepositorio;
import br.com.autobots.automanager.repositorios.EmpresaRepositorio;
import br.com.autobots.automanager.repositorios.UsuarioRepositorio;
import br.com.autobots.automanager.entidades.Empresa;

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
  private EmpresaRepositorio empresaRepositorio;

  @Autowired
  private CredencialRepositorio credencialRepositorio;

  @Autowired
  private AutenticacaoProvedor autenticacaoProvedor;

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
  @GetMapping("/{empresaId}/usuarios")
  @Operation(summary = "Obter todos os usuarios", description = "Retorna uma lista de todos os usuarios cadastrados")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Usuarios encontrados", content = @Content(schema = @Schema(implementation = List.class))),
      @ApiResponse(responseCode = "404", description = "Nenhum usuario cadastrado"),
      @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
  })
  public ResponseEntity<List<Usuario>> obterUsuarios(@PathVariable long empresaId) {
    var usuarios = obterUsuariosServico.obterUsuarios(empresaId);
    adicionaLinkUsuarioServico.adicionarLink(usuarios, empresaId);
    return ResponseEntity.status(HttpStatus.OK).body(usuarios);
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR', 'CLIENTE')")
  @GetMapping("/{empresaId}/usuario/{id}")
  @Operation(summary = "Obter usuario", description = "Retorna um usuario específico com base no ID fornecido")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Usuario encontrado", content = @Content(schema = @Schema(implementation = Usuario.class))),
      @ApiResponse(responseCode = "404", description = "Usuario não encontrado"),
      @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
  })
  public ResponseEntity<Usuario> obterUsuario(@PathVariable long id, @PathVariable long empresaId) {
    Optional<Empresa> empresa = empresaRepositorio.findById(empresaId);
    if (empresa.isEmpty()) {
      throw new NaoEncontradoExcecao("Empresa não encontrada");
    }
    var usuario = obterUsuarioServico.obterUsuario(id);
    adicionaLinkUsuarioServico.adicionarLink(usuario, empresaId);
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
    autenticacaoProvedor.registrar(usuario);
    usuarioRepositorio.save(usuario);
    empresa.get().getUsuarios().add(usuario);
    empresaRepositorio.save(empresa.get());
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
      @ApiResponse(responseCode = "404", description = "Empresa não encontrada"),
      @ApiResponse(responseCode = "409", description = "Usuario já cadastrado com esse nome de usuário"),
      @ApiResponse(responseCode = "409", description = "Usuario já cadastrado com essa código de barras"),
      @ApiResponse(responseCode = "409", description = "Documento já cadastrado"),
      @ApiResponse(responseCode = "409", description = "Usuario já cadastrado")
  })
  public ResponseEntity<?> atualizarUsuario(@RequestBody Usuario usuarioAtualizacao, @PathVariable long empresaId) {
    atualizaUsuarioServico.atualizar(empresaId, usuarioAtualizacao);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
  @DeleteMapping("/{empresaId}/usuario/excluir")
  @Operation(summary = "Excluir usuario", description = "Exclui um usuario existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Usuario excluído com sucesso"),
      @ApiResponse(responseCode = "404", description = "Usuario não encontrado"),
      @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
  })
  public ResponseEntity<?> excluirUsuario(@RequestBody Usuario exclusao, @PathVariable long empresaId) {
    excluirUsuarioServico.excluir(empresaId, exclusao);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}