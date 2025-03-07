package br.com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import br.com.autobots.automanager.controladores.ClienteControlador;
import br.com.autobots.automanager.entidades.Cliente;

@Component
public class AdicionaLinkClienteServico implements AdicionaLinkServico<Cliente> {

	@Override
	public void adicionarLink(List<Cliente> clientes) {
		for (Cliente cliente : clientes) {
			long id = cliente.getId();
			Link linkProprio = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder.methodOn(ClienteControlador.class).obterCliente(id))
					.withSelfRel();
			cliente.add(linkProprio);
		}
	}

	@Override
	public void adicionarLink(Cliente cliente) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(ClienteControlador.class)
						.obterClientes())
				.withRel("clientes");
		cliente.add(linkProprio);
	}
}