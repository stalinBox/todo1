package ec.gob.mag.domain.constraint;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
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
@ToString(of = "notId")
@EqualsAndHashCode(of = "notId")
@Builder

@Data
@Entity
public class NoticiasCreate implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla", required = true, readOnly = true)
	@JsonProperty("notId")
	private Long notId;

	@ApiModelProperty(value = "Ejemplo parametro String", example = "Titulo noticia")
	@Size(min = 0, max = 255, message = "_error.validation_range.message-[0, 255]")
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("notTitulo")
	@JsonInclude(Include.NON_NULL)
	private String notTitulo;

	@ApiModelProperty(value = "Ejemplo parametro String", example = "Titulo noticia")
	@JsonProperty("notDescripcion")
	@JsonInclude(Include.NON_NULL)
	@NotNull(message = "_error.validation_blank.message")
	private String notDescripcion;

	@ApiModelProperty(value = "Campo para guardar la imagen")
	@JsonProperty("notImagen")
	@JsonInclude(Include.NON_NULL)
	private byte[] notImagen;

	@ApiModelProperty(value = "Este campo almacena los valores f =false para eliminado logico  y t= true para indicar que está activo", required = true, allowableValues = "false=>no eliminado lógico, true=> eliminado lógico", example = "")
	@JsonProperty("notEstadoPubli")
	@JsonInclude(Include.NON_NULL)
	@NotNull(message = "_error.validation_blank.message")
	private Boolean notEstadoPubli;

	@ApiModelProperty(value = "Id de usuario que creó el regristro")
	@JsonProperty("notRegUsu")
	@JsonInclude(Include.NON_NULL)
	@NotNull(message = "_error.validation_blank.message")
	private Integer notRegUsu;

	@ApiModelProperty(value = "Ejemplo parametro String", example = "Link URL")
	@Column(name = "not_url")
	@JsonProperty("notUrl")
	@JsonInclude(Include.NON_NULL)
	private String notUrl;
	/******************************************************
	 * SECCION - RELACIONES JPA
	 ******************************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "are_id")
	@NotNull(message = "_error.validation_blank.message")
	private Area area;

}
