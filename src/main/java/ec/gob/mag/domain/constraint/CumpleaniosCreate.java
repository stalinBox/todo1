package ec.gob.mag.domain.constraint;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import ec.gob.mag.domain.Area;

import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString(of = "cumId")
@EqualsAndHashCode(of = "cumId")
@Builder

@Data
@Entity
public class CumpleaniosCreate implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla", required = true, readOnly = true)
	@JsonProperty("cumId")
	private Long cumId;

	@ApiModelProperty(value = "Ejemplo parametro String", example = "Nombres Completos")
	@Size(min = 0, max = 100, message = "_error.validation_range.message-[0, 100]")
	@JsonProperty("cumNombresCompletos")
	@JsonInclude(Include.NON_NULL)
	@NotNull(message = "_error.validation_blank.message")
	private String cumNombresCompletos;

	@ApiModelProperty(value = "Fecha de registro del campo")
	@JsonProperty("cumFechaNac")
	@JsonInclude(Include.NON_NULL)
	@NotNull(message = "_error.validation_blank.message")
	private Date cumFechaNac;

	@ApiModelProperty(value = "Campo para guardar la imagen")
	@JsonProperty("cumImagen")
	@JsonInclude(Include.NON_NULL)
	private String cumImagen;

	@ApiModelProperty(value = "Id de usuario que creó el regristro")
	@JsonProperty("cumRegUsu")
	@JsonInclude(Include.NON_NULL)
	@NotNull(message = "_error.validation_blank.message")
	private Integer cumRegUsu;

	/******************************************************
	 * SECCION - RELACIONES JPA
	 ******************************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "are_id")
	@NotNull(message = "_error.validation_blank.message")
	private Area area;

}
