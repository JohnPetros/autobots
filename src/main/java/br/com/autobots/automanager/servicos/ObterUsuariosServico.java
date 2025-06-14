package br.com.autobots.automanager.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.automanager.entidades.Empresa;
import br.com.autobots.automanager.entidades.Usuario;
import br.com.autobots.automanager.enums.PerfilUsuario;
import br.com.autobots.automanager.excecoes.NaoEncontradoExcecao;
import br.com.autobots.automanager.provedores.AutenticacaoProvedor;
import br.com.autobots.automanager.repositorios.EmpresaRepositorio;

@Service
public class ObterUsuariosServico {
  @Autowired
  private EmpresaRepositorio empresaRepositorio;

  @Autowired
  private AutenticacaoProvedor autenticacaoProvedor;

  public List<Usuario> obterUsuarios(Long empresaId) {
    Optional<Empresa> empresa = empresaRepositorio.findById(empresaId);
    if (empresa.isEmpty()) {
      throw new NaoEncontradoExcecao("Empresa não encontrada");
    }
    var usuarios = empresa.get().obterUsuariosPorPerfil(autenticacaoProvedor.getPerfil());
    if (usuarios.isEmpty()) {
      throw new NaoEncontradoExcecao("Nenhum usuario cadastrado");
    }
    return usuarios;
  }
}
