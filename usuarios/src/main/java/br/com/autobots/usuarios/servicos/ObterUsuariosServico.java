package br.com.autobots.usuarios.servicos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.usuarios.entidades.Usuario;
import br.com.autobots.usuarios.enums.PerfilUsuario;
import br.com.autobots.usuarios.excecoes.NaoEncontradoExcecao;
import br.com.autobots.usuarios.provedores.AutenticacaoProvedor;
import br.com.autobots.usuarios.repositorios.UsuarioRepositorio;

@Service
public class ObterUsuariosServico {
  @Autowired
  private AutenticacaoProvedor autenticacaoProvedor;

  @Autowired
  private UsuarioRepositorio usuarioRepositorio;

  public List<Usuario> obterUsuarios(Long empresaId, String tipo) {
    var usuarios = obterUsuariosPorPerfil(autenticacaoProvedor.getPerfil(), empresaId, tipo);
    if (usuarios.isEmpty()) {
      throw new NaoEncontradoExcecao("Nenhum usuario cadastrado");
    }
    return usuarios;
  }

  public List<Usuario> obterUsuariosPorPerfil(PerfilUsuario perfil, Long empresaId, String tipo) {
    List<Usuario> usuarios = new ArrayList<>();

    if (tipo.equals("todos")) {
      usuarios = obterTodosUsuarios(empresaId);
    } else if (tipo.equals("clientes")) {
      usuarios = obterClientes(empresaId);
    } else if (tipo.equals("funcionarios")) {
      usuarios = obterFuncionarios(empresaId);
    }
    switch (perfil) {
      case ADMIN:
        return usuarios;
      case GERENTE:
        return usuarios
            .stream()
            .filter(usuario -> usuario.getPerfil() != PerfilUsuario.ADMIN)
            .collect(Collectors.toList());
      case VENDEDOR:
        return usuarios
            .stream()
            .filter(usuario -> usuario.getPerfil() == PerfilUsuario.CLIENTE)
            .collect(Collectors.toList());
      default:
        return usuarios;
    }
  }

  private List<Usuario> obterTodosUsuarios(Long empresaId) {
    return usuarioRepositorio.findByEmpresaId(empresaId);
  }

  private List<Usuario> obterClientes(Long empresaId) {
    return usuarioRepositorio.findByEmpresaIdAndPerfil(empresaId, PerfilUsuario.CLIENTE);
  }

  private List<Usuario> obterFuncionarios(Long empresaId) {
    return usuarioRepositorio.findByEmpresaIdAndPerfilIn(empresaId,
        List.of(PerfilUsuario.VENDEDOR, PerfilUsuario.GERENTE));
  }
}
