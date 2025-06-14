package br.com.autobots.automanager.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autobots.automanager.entidades.Usuario;
import br.com.autobots.automanager.enums.PerfilUsuario;
import br.com.autobots.automanager.entidades.Credencial;
import br.com.autobots.automanager.entidades.Documento;
import br.com.autobots.automanager.entidades.Endereco;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
  Optional<Usuario> findByEndereco(Endereco endereco);

  Optional<Usuario> findByCredencial(Credencial credencial);

  Optional<Usuario> findByCredencialNomeUsuario(String nomeUsuario);

  Optional<Usuario> findByIdAndPerfil(Long id, PerfilUsuario perfil);

  Optional<Usuario> findByDocumentos(List<Documento> documentos);

  Optional<Usuario> findByEmailsEndereco(String endereco);
}
