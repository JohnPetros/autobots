package br.com.autobots.server.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EnderecoDto {
  @Schema(description = "Estado do endereço", example = "SP")
  private String estado;
  @Schema(description = "Cidade do endereço", example = "São Paulo")
  private String cidade;
  @Schema(description = "Bairro do endereço", example = "Jardim Paulista")
  private String bairro;
  @Schema(description = "Rua do endereço", example = "Rua das Flores")
  private String rua;
  @Schema(description = "Número do endereço", example = "123")
  private String numero;
  @Schema(description = "CEP do endereço", example = "01001-000")
  private String codigoPostal;
  @Schema(description = "Informações adicionais do endereço", example = "Apto 101")
  private String informacoesAdicionais;
  @Schema(description = "ID do cliente proprietário do endereço", example = "1")
  private Long clienteId;
}
