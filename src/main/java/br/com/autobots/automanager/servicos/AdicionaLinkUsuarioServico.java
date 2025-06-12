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
	public void adicionarLink(List<Usuario> usuarios, Long empresaId) {
		for (Usuario usuario : usuarios) {
			adicionarLink(usuario, empresaId);
		}
	}

	@Override
	public void adicionarLink(Usuario usuario, Long empresaId) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(UsuarioControlador.class)
						.obterUsuarios(empresaId))
				.withRel("obter-todos");
		Link linkCadastrar = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(UsuarioControlador.class)
						.cadastrarUsuario(null, empresaId))
				.withRel("cadastrar");
		Link linkAtualizar = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(UsuarioControlador.class)
						.atualizarUsuario(null, empresaId))
				.withRel("atualizar");
		Link linkExcluir = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(UsuarioControlador.class)
						.excluirUsuario(null, empresaId))
				.withRel("excluir");
		if (!usuario.getDocumentos().isEmpty()) {
			adicionaLinkDocumentoServico.adicionarLink(usuario.getDocumentos(), empresaId);
		}
		if (!usuario.getTelefones().isEmpty()) {
			adicionaLinkTelefoneServico.adicionarLink(usuario.getTelefones(), empresaId);
		}
		if (usuario.getEndereco() != null) {
			adicionaLinkEnderecoServico.adicionarLink(usuario.getEndereco(), empresaId);
		}
		if (!usuario.getVeiculos().isEmpty()) {
			adicionaLinkVeiculoServico.adicionarLink(usuario.getVeiculos(), empresaId);
		}
		usuario.add(linkProprio);
		usuario.add(linkCadastrar);
		usuario.add(linkAtualizar);
		usuario.add(linkExcluir);
	}
}