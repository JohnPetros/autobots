package br.com.autobots.autenticacao.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.autobots.autenticacao.entidades.Credencial;
import br.com.autobots.autenticacao.provedores.JwtProvedor;
import br.com.autobots.autenticacao.repositorios.CredencialRepositorio;

@Service
public class RegistraCredencialServico {
  @Autowired
  private JwtProvedor jwtProvedor;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private CredencialRepositorio credencialRepositorio;

  public String registrar(Credencial credencial) {
    var encryptedPassword = passwordEncoder.encode(credencial.getSenha());
    credencial.setSenha(encryptedPassword);
    credencialRepositorio.save(credencial);
    var jwt = jwtProvedor.gerarToken(credencial.getEmail());
    return jwt;
  }
}
