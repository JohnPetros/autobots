package br.com.autobots.automanager.entidades;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "telefones")
public class Telefone extends RepresentationModel<Endereco> {
	@Id()
	@Schema(description = "ID do telefone", example = "1")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	@Schema(description = "DDD do telefone", example = "11")
	private String ddd;

	@Column
	@Schema(description = "Número do telefone", example = "99999-9999")
	private String numero;

	@Column(name = "cliente_id")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Long clienteId;
}