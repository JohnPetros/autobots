package br.com.autobots.automanager.entidades;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class Telefone extends RepresentationModel<Telefone> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "ID do telefone", example = "1")
	private Long id;

	@Column
	@Schema(description = "DDD do telefone", example = "11")
	@NotBlank(message = "DDD é obrigatório")
	@Pattern(regexp = "\\d{2}", message = "DDD deve conter 2 dígitos")
	private String ddd;

	@Column
	@Schema(description = "Número do telefone", example = "99999-9999")
	@NotBlank(message = "Número é obrigatório")
	@Pattern(regexp = "\\d{9}", message = "Número deve conter 9 dígitos")
	private String numero;

	@Column(name = "cliente_id")
	@JsonIgnore
	private Long clienteId;
}