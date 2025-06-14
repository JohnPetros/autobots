package br.com.autobots.automanager.servicos;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autobots.automanager.entidades.Empresa;
import br.com.autobots.automanager.entidades.Venda;
import br.com.autobots.automanager.enums.PerfilUsuario;
import br.com.autobots.automanager.excecoes.NaoEncontradoExcecao;
import br.com.autobots.automanager.repositorios.EmpresaRepositorio;
import br.com.autobots.automanager.repositorios.VendaRepositorio;
import br.com.autobots.automanager.provedores.AutenticacaoProvedor;

@Service
public class CadastrarVendaServico {
  @Autowired
  private EmpresaRepositorio empresaRepositorio;

  @Autowired
  private ValidaVendaServico validaVendaServico;

  @Autowired
  private VendaRepositorio vendaRepositorio;

  @Autowired
  private AutenticacaoProvedor autenticacaoProvedor;

  public void cadastrarVenda(Long empresaId, Venda venda) {
    Optional<Empresa> empresa = empresaRepositorio.findById(empresaId);
    if (empresa.isEmpty()) {
      throw new NaoEncontradoExcecao("Empresa não encontrada");
    }
    validaVendaServico.validar(venda);
    vendaRepositorio.save(venda);

    var perfil = autenticacaoProvedor.getPerfil();
    if (perfil == PerfilUsuario.VENDEDOR) {
      var usuario = autenticacaoProvedor.getUsuario();
      venda.setVendedor(usuario);
    }

    empresa.get().getVendas().add(venda);
    empresaRepositorio.save(empresa.get());
  }
}
