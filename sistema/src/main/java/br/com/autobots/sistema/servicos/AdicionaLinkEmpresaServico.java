package br.com.autobots.sistema.servicos;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

import br.com.autobots.sistema.controladores.EmpresaControlador;
import br.com.autobots.sistema.entidades.Empresa;

@Service
public class AdicionaLinkEmpresaServico implements AdicionaLinkServico<Empresa> {
  @Override
  public void adicionarLink(List<Empresa> empresas, Long empresaId) {
    for (Empresa empresa : empresas) {
      adicionarLink(empresa, empresa.getId());
    }
  }

  @Override
  public void adicionarLink(Empresa empresa, Long empresaId) {
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
    empresa.add(linkObter);
    empresa.add(linkCadastrar);
    empresa.add(linkAtualizar);
    empresa.add(linkExcluir);
  }
}
