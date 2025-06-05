package br.com.autobots.automanager.servicos;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

import br.com.autobots.automanager.controladores.EmpresaControlador;
import br.com.autobots.automanager.entidades.Empresa;

@Service
public class AdicionaLinkEmpresaServico implements AdicionaLinkServico<Empresa> {
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
    var linkObterTodos = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(EmpresaControlador.class)
            .obterEmpresas())
        .withRel("obter todos os empresas");
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
    empresa.add(linkObterTodos);
    empresa.add(linkCadastrar);
    empresa.add(linkAtualizar);
    empresa.add(linkExcluir);
  }
}
