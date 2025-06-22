package br.com.autobots.usuarios.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autobots.usuarios.entidades.Usuario;
import br.com.autobots.usuarios.enums.PerfilUsuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
  List<Usuario> findByEmpresaId(Long empresaId);

  Optional<Usuario> findByIdAndPerfil(Long id, PerfilUsuario perfil);

  List<Usuario> findByEmpresaIdAndPerfil(Long empresaId, PerfilUsuario perfil);

  Optional<Usuario> findByEmpresaIdAndEmail(Long empresaId, String email);

  Optional<Usuario> findByEmail(String email);

  List<Usuario> findByEmpresaIdAndPerfilIn(Long empresaId, List<PerfilUsuario> perfis);
}
