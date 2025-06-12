package br.com.autobots.automanager.servicos;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import br.com.autobots.automanager.controladores.EmpresaControlador;
import br.com.autobots.automanager.entidades.Empresa;

@Service
public class AdicionaLinkEmpresaServico implements AdicionaLinkServico<Empresa> {
  @Autowired
  private AdicionaLinkTelefoneServico adicionaLinkTelefoneServico;

  @Autowired
  private AdicionaLinkEnderecoServico adicionaLinkEnderecoServico;

  @Autowired
  private AdicionaLinkMercadoriaServico adicionaLinkMercadoriaServico;

  @Autowired
  private AdicionaLinkServicoServico adicionaLinkServicoServico;

  @Autowired
  private AdicionaLinkUsuarioServico adicionaLinkUsuarioServico;

  @Autowired
  private AdicionaLinkVendaServico adicionaLinkVendaServico;

  @Override
  public void adicionarLink(List<Empresa> empresas) {
    for (Empresa empresa : empresas) {
      adicionarLink(empresa);
    }
  }

  @Override
  public void adicionarLink(Empresa empresa) {
    var linkObter = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(EmpresaControlador.class)
            .obterEmpresa(empresa.getId()))
        .withRel("obter empresa");
    var linkCadastrar = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(EmpresaControlador.class)
            .cadastrarEmpresa(null))
        .withRel("cadastrar empresa");
    var linkAtualizar = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(EmpresaControlador.class)
            .atualizarEmpresa(null))
        .withRel("atualizar empresa");
    var linkExcluir = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(EmpresaControlador.class)
            .excluirEmpresa(null))
        .withRel("excluir empresa");
    adicionaLinkTelefoneServico.adicionarLink(empresa.getTelefones());
    adicionaLinkEnderecoServico.adicionarLink(empresa.getEndereco());
    if (!empresa.getMercadorias().isEmpty()) {
      adicionaLinkMercadoriaServico.adicionarLink(empresa.getMercadorias());
    }
    if (!empresa.getServicos().isEmpty()) {
      adicionaLinkServicoServico.adicionarLink(empresa.getServicos());
    }
    if (!empresa.getUsuarios().isEmpty()) {
      adicionaLinkUsuarioServico.adicionarLink(empresa.getUsuarios());
    }
    if (!empresa.getVendas().isEmpty()) {
      adicionaLinkVendaServico.adicionarLink(empresa.getVendas());
    }
    empresa.add(linkObter);
    empresa.add(linkCadastrar);
    empresa.add(linkAtualizar);
    empresa.add(linkExcluir);
  }
}
