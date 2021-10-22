package ec.gob.mag.domain.constraint;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
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
public class CumpleaniosUpdate implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla", required = true, readOnly = true)
	@Column(name = "cum_id")
	@JsonProperty("cumId")
	@NotNull(message = "_error.validation_blank.message")
	private Long cumId;

	@ApiModelProperty(value = "Ejemplo parametro String", example = "Nombres Completos")
	@Size(min = 0, max = 100, message = "_error.validation_range.message-[0, 100]")
	@Column(name = "cum_nombres_completos")
	@JsonProperty("cumNombresCompletos")
	@JsonInclude(Include.NON_NULL)
	private String cumNombresCompletos;

	@ApiModelProperty(value = "Fecha de registro del campo")
	@Column(name = "cum_fecha_nac", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@JsonProperty("cumFechaNac")
	@JsonInclude(Include.NON_NULL)
	private Date cumFechaNac;

	@ApiModelProperty(value = "Campo para guardar la imagen")
	@Column(name = "cum_imagen")
	@JsonProperty("cumImagen")
	@JsonInclude(Include.NON_NULL)
	private String cumImagen;

	@ApiModelProperty(value = "Id de usuario que actualizacio del campo", example = "")
	@Column(name = "cum_act_usu")
	@JsonProperty("cumActUsu")
	@NotNull(message = "_error.validation_blank.message")
	private Integer cumActUsu;

	/******************************************************
	 * SECCION - RELACIONES JPA
	 ******************************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "are_id")
	private Area area;

}
