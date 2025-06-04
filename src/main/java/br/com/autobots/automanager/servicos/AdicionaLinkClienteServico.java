package br.com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import br.com.autobots.automanager.controladores.ClienteControlador;
import br.com.autobots.automanager.entidades.Cliente;

@Service
public class AdicionaLinkClienteServico implements AdicionaLinkServico<Cliente> {
	@Autowired
	private AdicionaLinkDocumentoServico adicionaLinkDocumentoServico;

	@Autowired
	private AdicionaLinkTelefoneServico adicionaLinkTelefoneServico;

	@Autowired
	private AdicionaLinkEnderecoServico adicionaLinkEnderecoServico;

	@Override
	public void adicionarLink(List<Cliente> clientes) {
		for (Cliente cliente : clientes) {
			adicionarLink(cliente);
		}
	}

	@Override
	public void adicionarLink(Cliente cliente) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(ClienteControlador.class)
						.obterClientes())
				.withRel("obter-todos");
		Link linkCadastrar = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(ClienteControlador.class)
						.cadastrarCliente(null))
				.withRel("cadastrar");
		Link linkAtualizar = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(ClienteControlador.class)
						.atualizarCliente(null))
				.withRel("atualizar");
		Link linkExcluir = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(ClienteControlador.class)
						.excluirCliente(null))
				.withRel("excluir");
		adicionaLinkDocumentoServico.adicionarLink(cliente.getDocumentos());
		adicionaLinkTelefoneServico.adicionarLink(cliente.getTelefones());
		if (cliente.getEndereco() != null) {
			adicionaLinkEnderecoServico.adicionarLink(cliente.getEndereco());
		}
		cliente.add(linkProprio);
		cliente.add(linkCadastrar);
		cliente.add(linkAtualizar);
		cliente.add(linkExcluir);
	}
}