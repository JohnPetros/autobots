package br.com.autobots.server.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DocumentoDto {
  @Schema(description = "Tipo do documento", example = "CPF")
  private String tipo;
  @Schema(description = "Número do documento", example = "1234567890")
  private String numero;
  @Schema(description = "ID do cliente proprietário do documento", example = "1")
  private Long clienteId;
}
