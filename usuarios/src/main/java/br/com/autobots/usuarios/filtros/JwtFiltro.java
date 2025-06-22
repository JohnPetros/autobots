package br.com.autobots.usuarios.filtros;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.autobots.usuarios.entidades.Usuario;
import br.com.autobots.usuarios.provedores.JwtProvedor;
import br.com.autobots.usuarios.repositorios.UsuarioRepositorio;
import br.com.autobots.usuarios.seguranca.SegurancaUsuario;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFiltro extends OncePerRequestFilter {
  @Autowired
  JwtProvedor jwtProvedor;

  @Autowired
  UsuarioRepositorio usuarioRepositorio;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {

    var token = recoverToken(request);
    if (token != null) {
      var subject = jwtProvedor.validarToken(token);
      System.out.println("Subject: " + subject);
      try {
        var usuario = getUsuario(subject);
        var segurancaUsuario = new SegurancaUsuario(usuario);
        var authentication = new UsernamePasswordAuthenticationToken(
            segurancaUsuario, null,
            segurancaUsuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
      } catch (Exception e) {
        System.out.println("Erro ao autenticar usuário: " + e.getMessage());
        throw new BadCredentialsException("Credenciais inválidas");
      }
    }
    filterChain.doFilter(request, response);
  }

  private String recoverToken(HttpServletRequest request) {
    var authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return null;
    }
    return authHeader.replace("Bearer ", "");
  }

  private Usuario getUsuario(String usuarioEmail) {
    var usuario = usuarioRepositorio.findByEmail(usuarioEmail);
    if (usuario.isEmpty()) {
      throw new BadCredentialsException("Credenciais inválidas");
    }
    System.out.println("Usuario encontrado: " + usuario.get().getPerfil());
    return usuario.get();
  }
}