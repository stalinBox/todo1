package ec.gob.mag.domain.constraint;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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
public class NoticiasUpdate implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla", required = true, readOnly = true)
	@JsonProperty("notId")
	@NotNull(message = "_error.validation_blank.message")
	private Long notId;

	@ApiModelProperty(value = "Ejemplo parametro String", example = "Titulo noticia")
//	@Size(min = 0, max = 255, message = "_error.validation_range.message-[0, 255]")
	@JsonProperty("notTitulo")
	@JsonInclude(Include.NON_NULL)
	private String notTitulo;

	@ApiModelProperty(value = "Ejemplo parametro String", example = "Titulo noticia")
	@JsonProperty("notDescripcion")
	@JsonInclude(Include.NON_NULL)
	private String notDescripcion;

	@ApiModelProperty(value = "Campo para guardar la imagen")
	@JsonProperty("notImagen")
	@JsonInclude(Include.NON_NULL)
	private byte[] notImagen;

	@ApiModelProperty(value = "Este campo almacena los valores f =false para eliminado logico  y t= true para indicar que está activo", required = true, allowableValues = "false=>no eliminado lógico, true=> eliminado lógico", example = "")
	@JsonProperty("notEstadoPubli")
	@JsonInclude(Include.NON_NULL)
	private Boolean notEstadoPubli;

	@ApiModelProperty(value = "Id de usuario que actualizacio del campo", example = "")
	@Column(name = "not_act_usu")
	@JsonProperty("notActUsu")
	@NotNull(message = "_error.validation_blank.message")
	private Integer notActUsu;

	/******************************************************
	 * SECCION - RELACIONES JPA
	 ******************************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "are_id")
	private Area area;
	/*****************************************************
	 * SECCION - CAMPOS POR DEFECTO EN TODAS LAS ENTIDADES
	 *****************************************************/
//	@ApiModelProperty(value = "11=activo  12=inactivo", required = true, allowableValues = "11=>activo, 12=>inactivo", example = "11")
//	@Column(name = "not_estado", columnDefinition = "Integer default 11")
//	@JsonProperty("notEstado")
//	@JsonInclude(Include.NON_NULL)
//	private Integer notEstado;
//
//	@ApiModelProperty(value = "Fecha de registro del campo", example = "")
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "not_reg_fecha", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
//	@JsonProperty("notRegFecha")
//	@JsonInclude(Include.NON_NULL)
//	private Date notRegFecha;
//
//	@ApiModelProperty(value = "Fecha en la que hizo la actualización del registro", example = "")
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "not_act_fecha")
//	@JsonProperty("notActFecha")
//	@JsonInclude(Include.NON_NULL)
//	private Date notActFecha;
//
//	@ApiModelProperty(value = "Id de usuario que actualizacio del campo", example = "")
//	@Column(name = "not_act_usu")
//	@JsonProperty("notActUsu")
//	private Integer notActUsu;
//
//	@ApiModelProperty(value = "Este campo almacena los valores f =false para eliminado logico  y t= true para indicar que está activo", required = true, allowableValues = "false=>no eliminado lógico, true=> eliminado lógico", example = "")
//	@Column(name = "not_eliminado", columnDefinition = "boolean default false")
//	@JsonProperty("cEliminado")
//	@JsonInclude(Include.NON_NULL)
//	private Boolean notEliminado;

}
