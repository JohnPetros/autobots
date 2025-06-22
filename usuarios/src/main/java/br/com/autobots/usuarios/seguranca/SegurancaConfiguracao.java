package br.com.autobots.usuarios.seguranca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.autobots.usuarios.filtros.JwtFiltro;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SegurancaConfiguracao {
  @Autowired
  private JwtFiltro jwtFiltro;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(crsf -> crsf.disable());
    http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    http.authorizeHttpRequests(auth -> auth
        .requestMatchers(HttpMethod.POST, "/autenticacao/login").permitAll()
        .requestMatchers(HttpMethod.GET, "/h2/**").permitAll()
        .requestMatchers(HttpMethod.POST, "/h2/**").permitAll()
        .anyRequest()
        .authenticated());
    http.addFilterBefore(
        jwtFiltro,
        UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    UrlBasedCorsConfigurationSource fonte = new UrlBasedCorsConfigurationSource();
    fonte.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
    return fonte;
  }
}