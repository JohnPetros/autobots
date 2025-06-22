package br.com.autobots.mercadorias.servicos;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

import br.com.autobots.mercadorias.controladores.ServicoControlador;
import br.com.autobots.mercadorias.entidades.Servico;

@Service
public class AdicionaLinkServicoServico implements AdicionaLinkServico<Servico> {
    @Override
    public void adicionarLink(List<Servico> servicos, Long empresaId) {
        for (Servico servico : servicos) {
            adicionarLink(servico, empresaId);
        }
    }

    @Override
    public void adicionarLink(Servico servico, Long empresaId) {
        var linkObter = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(ServicoControlador.class)
                        .obterServico(servico.getId(), empresaId))
                .withRel("obter servico");
        var linkObterTodos = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(ServicoControlador.class)
                        .obterServicos(empresaId))
                .withRel("obter todos os servicos");
        var linkCadastrar = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(ServicoControlador.class)
                        .cadastrarServico(null, empresaId))
                .withRel("cadastrar servico");
        var linkAtualizar = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(ServicoControlador.class)
                        .atualizarServico(null, empresaId))
                .withRel("atualizar servico");
        var linkExcluir = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(ServicoControlador.class)
                        .excluirServico(null, empresaId))
                .withRel("excluir servico");
        servico.add(linkObter);
        servico.add(linkObterTodos);
        servico.add(linkCadastrar);
        servico.add(linkAtualizar);
        servico.add(linkExcluir);
    }
}
