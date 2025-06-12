package br.com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import br.com.autobots.automanager.controladores.UsuarioControlador;
import br.com.autobots.automanager.entidades.Usuario;

@Service
public class AdicionaLinkUsuarioServico implements AdicionaLinkServico<Usuario> {
	@Autowired
	private AdicionaLinkDocumentoServico adicionaLinkDocumentoServico;

	@Autowired
	private AdicionaLinkTelefoneServico adicionaLinkTelefoneServico;

	@Autowired
	private AdicionaLinkEnderecoServico adicionaLinkEnderecoServico;

	@Autowired
	private AdicionaLinkVeiculoServico adicionaLinkVeiculoServico;

	@Override
	public void adicionarLink(List<Usuario> usuarios) {
		for (Usuario usuario : usuarios) {
			adicionarLink(usuario);
		}
	}

	@Override
	public void adicionarLink(Usuario usuario) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(UsuarioControlador.class)
						.obterUsuarios())
				.withRel("obter-todos");
		Link linkCadastrar = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(UsuarioControlador.class)
						.cadastrarUsuario(null))
				.withRel("cadastrar");
		Link linkAtualizar = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(UsuarioControlador.class)
						.atualizarUsuario(null))
				.withRel("atualizar");
		Link linkExcluir = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(UsuarioControlador.class)
						.excluirUsuario(null))
				.withRel("excluir");
		if (!usuario.getDocumentos().isEmpty()) {
			adicionaLinkDocumentoServico.adicionarLink(usuario.getDocumentos());
		}
		if (!usuario.getTelefones().isEmpty()) {
			adicionaLinkTelefoneServico.adicionarLink(usuario.getTelefones());
		}
		if (usuario.getEndereco() != null) {
			adicionaLinkEnderecoServico.adicionarLink(usuario.getEndereco());
		}
		if (!usuario.getVeiculos().isEmpty()) {
			adicionaLinkVeiculoServico.adicionarLink(usuario.getVeiculos());
		}
		usuario.add(linkProprio);
		usuario.add(linkCadastrar);
		usuario.add(linkAtualizar);
		usuario.add(linkExcluir);
	}
}