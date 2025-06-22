package br.com.autobots.usuarios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.autobots.usuarios.entidades.Documento;
import br.com.autobots.usuarios.entidades.Endereco;
import br.com.autobots.usuarios.entidades.Usuario;
import br.com.autobots.usuarios.entidades.Telefone;
import br.com.autobots.usuarios.enums.PerfilUsuario;
import br.com.autobots.usuarios.enums.TipoDocumento;
import br.com.autobots.usuarios.repositorios.UsuarioRepositorio;

@Component
public class InserirDadosIniciais implements CommandLineRunner {
  @Autowired
  private UsuarioRepositorio usuarioRepositorio;

  @Override
  public void run(String... args) throws Exception {
    var admin = inserirUsuarioAdmin();
    var clientes = inserirUsuarioClientes();
    var funcionarios = inserirUsuarioFuncionarios();

    usuarioRepositorio.save(admin);
    usuarioRepositorio.saveAll(clientes);
    usuarioRepositorio.saveAll(funcionarios);
  }

  private Usuario inserirUsuarioAdmin() {
    var usuario = new Usuario();
    var telefone = new Telefone();
    var documento = new Documento();
    var endereco = new Endereco();

    documento.setTipo(TipoDocumento.CPF);
    documento.setNumero("00011122233");

    endereco.setBairro("Bairro Exemplo");
    endereco.setCidade("Cidade Exemplo");
    endereco.setEstado("SP");
    endereco.setRua("Rua Exemplo");
    endereco.setNumero("123");
    endereco.setCodigoPostal("1234567890");
    endereco.setInformacoesAdicionais("Apto 123");

    telefone.setDdd("11");
    telefone.setNumero("999999999");

    usuario.setNome("Admin");
    usuario.setNomeSocial("Admin");
    usuario.setEmail("admin@autobots.com");
    usuario.setTelefones(List.of(telefone));
    usuario.setDocumentos(List.of(documento));
    usuario.setEndereco(endereco);
    usuario.setPerfil(PerfilUsuario.ADMIN);
    usuario.setInativo(false);
    usuario.setEmpresaId(1L);

    return usuario;
  }

  private List<Usuario> inserirUsuarioClientes() {
    var usuario1 = new Usuario();
    var telefone1 = new Telefone();
    var documento1 = new Documento();
    var endereco1 = new Endereco();

    documento1.setTipo(TipoDocumento.CPF);
    documento1.setNumero("11144477755");

    endereco1.setBairro("Vila Nova");
    endereco1.setCidade("São Paulo");
    endereco1.setEstado("SP");
    endereco1.setRua("Rua das Flores");
    endereco1.setNumero("456");
    endereco1.setCodigoPostal("01234567");
    endereco1.setInformacoesAdicionais("Casa");

    telefone1.setDdd("11");
    telefone1.setNumero("988888888");

    usuario1.setNome("João Silva");
    usuario1.setNomeSocial("João");
    usuario1.setEmail("joao.silva@gmail.com");
    usuario1.setTelefones(List.of(telefone1));
    usuario1.setDocumentos(List.of(documento1));
    usuario1.setEndereco(endereco1);
    usuario1.setPerfil(PerfilUsuario.CLIENTE);
    usuario1.setInativo(false);
    usuario1.setEmpresaId(1L);

    var usuario2 = new Usuario();
    var telefone2 = new Telefone();
    var documento2 = new Documento();
    var endereco2 = new Endereco();

    documento2.setTipo(TipoDocumento.CPF);
    documento2.setNumero("22255588866");

    endereco2.setBairro("Centro");
    endereco2.setCidade("Rio de Janeiro");
    endereco2.setEstado("RJ");
    endereco2.setRua("Avenida Principal");
    endereco2.setNumero("789");
    endereco2.setCodigoPostal("20000000");
    endereco2.setInformacoesAdicionais("Apartamento 101");

    telefone2.setDdd("21");
    telefone2.setNumero("977777777");

    usuario2.setNome("Maria Santos");
    usuario2.setNomeSocial("Maria");
    usuario2.setEmail("maria.santos@outlook.com");
    usuario2.setTelefones(List.of(telefone2));
    usuario2.setDocumentos(List.of(documento2));
    usuario2.setEndereco(endereco2);
    usuario2.setPerfil(PerfilUsuario.CLIENTE);
    usuario2.setInativo(false);
    usuario2.setEmpresaId(1L);

    return List.of(usuario1, usuario2);
  }

  private List<Usuario> inserirUsuarioFuncionarios() {
    var usuario1 = new Usuario();
    var telefone1 = new Telefone();
    var documento1 = new Documento();
    var endereco1 = new Endereco();

    documento1.setTipo(TipoDocumento.CPF);
    documento1.setNumero("33366699900");

    endereco1.setBairro("Jardim Paulista");
    endereco1.setCidade("São Paulo");
    endereco1.setEstado("SP");
    endereco1.setRua("Avenida Paulista");
    endereco1.setNumero("1000");
    endereco1.setCodigoPostal("01310100");
    endereco1.setInformacoesAdicionais("Sala 501");

    telefone1.setDdd("11");
    telefone1.setNumero("966666666");

    usuario1.setNome("Carlos Oliveira");
    usuario1.setNomeSocial("Carlos");
    usuario1.setEmail("carlos.oliveira@autobots.com");
    usuario1.setTelefones(List.of(telefone1));
    usuario1.setDocumentos(List.of(documento1));
    usuario1.setEndereco(endereco1);
    usuario1.setPerfil(PerfilUsuario.GERENTE);
    usuario1.setInativo(false);
    usuario1.setEmpresaId(1L);

    var usuario2 = new Usuario();
    var telefone2 = new Telefone();
    var documento2 = new Documento();
    var endereco2 = new Endereco();

    documento2.setTipo(TipoDocumento.CPF);
    documento2.setNumero("44477700011");

    endereco2.setBairro("Copacabana");
    endereco2.setCidade("Rio de Janeiro");
    endereco2.setEstado("RJ");
    endereco2.setRua("Avenida Atlântica");
    endereco2.setNumero("2000");
    endereco2.setCodigoPostal("22070001");
    endereco2.setInformacoesAdicionais("Cobertura");

    telefone2.setDdd("21");
    telefone2.setNumero("955555555");

    usuario2.setNome("Ana Costa");
    usuario2.setNomeSocial("Ana");
    usuario2.setEmail("ana.costa@autobots.com");
    usuario2.setTelefones(List.of(telefone2));
    usuario2.setDocumentos(List.of(documento2));
    usuario2.setEndereco(endereco2);
    usuario2.setPerfil(PerfilUsuario.VENDEDOR);
    usuario2.setInativo(false);
    usuario2.setEmpresaId(1L);

    return List.of(usuario1, usuario2);
  }
}
