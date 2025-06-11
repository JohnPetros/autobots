package br.com.autobots.automanager.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autobots.automanager.entidades.Usuario;
import br.com.autobots.automanager.entidades.CredencialCodigoBarra;
import br.com.autobots.automanager.entidades.CredencialUsuarioSenha;
import br.com.autobots.automanager.entidades.Documento;
import br.com.autobots.automanager.entidades.Endereco;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
  Optional<Usuario> findByEndereco(Endereco endereco);

  Optional<Usuario> findByCredencialCodigoBarra(CredencialCodigoBarra credencialCodigoBarra);

  Optional<Usuario> findByDocumentos(List<Documento> documentos);
}
