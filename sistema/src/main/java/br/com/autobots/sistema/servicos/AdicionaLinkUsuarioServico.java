package br.com.autobots.sistema.servicos;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import br.com.autobots.sistema.controladores.UsuarioControlador;
import br.com.autobots.sistema.entidades.Usuario;

@Service
public class AdicionaLinkUsuarioServico implements AdicionaLinkServico<Usuario> {
	@Override
	public void adicionarLink(List<Usuario> usuarios, Long empresaId) {
		for (Usuario usuario : usuarios) {
			adicionarLink(usuario, empresaId);
		}
	}

	@Override
	public void adicionarLink(Usuario usuario, Long empresaId) {
		Link linkObterTodos = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(UsuarioControlador.class)
						.obterUsuarios(empresaId))
				.withRel("obter todos");
		Link linkObterPorId = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(UsuarioControlador.class)
						.obterUsuario(usuario.getId()))
				.withRel("obter por id");
		Link linkObterPorEmail = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(UsuarioControlador.class)
						.obterUsuario(usuario.getId()))
				.withRel("obter por email");
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
		usuario.add(linkObterTodos);
		usuario.add(linkObterPorId);
		usuario.add(linkObterPorEmail);
		usuario.add(linkCadastrar);
		usuario.add(linkAtualizar);
		usuario.add(linkExcluir);
	}
}