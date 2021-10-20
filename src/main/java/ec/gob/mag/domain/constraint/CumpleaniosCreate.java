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
	private byte[] cumImagen;

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
	/*****************************************************
	 * SECCION - CAMPOS POR DEFECTO EN TODAS LAS ENTIDADES
	 *****************************************************/
//	@ApiModelProperty(value = "11=activo  12=inactivo", required = true, allowableValues = "11=>activo, 12=>inactivo", example = "11")
//	@Column(name = "cum_estado", columnDefinition = "Integer default 11")
//	@JsonProperty("cumEstado")
//	@JsonInclude(Include.NON_NULL)
//	private Integer cumEstado;
//
//	@ApiModelProperty(value = "Fecha de registro del campo", example = "")
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "cum_reg_fecha", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
//	@JsonProperty("cumRegFecha")
//	@JsonInclude(Include.NON_NULL)
//	private Date cumRegFecha;
//
//	@ApiModelProperty(value = "Id de usuario que creó el regristro", example = "")
//	@Column(name = "cum_reg_usu", nullable = false)
//	@JsonProperty("cumRegUsu")
//	@JsonInclude(Include.NON_NULL)
//	@NotNull(message = "_error.validation_blank.message")
//	private Integer cumRegUsu;
//
//	@ApiModelProperty(value = "Fecha en la que hizo la actualización del registro", example = "")
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "cum_act_fecha")
//	@JsonProperty("cumActFecha")
//	@JsonInclude(Include.NON_NULL)
//	private Date cumActFecha;

//	@ApiModelProperty(value = "Id de usuario que actualizacio del campo", example = "")
//	@Column(name = "cum_act_usu")
//	@JsonProperty("cumActUsu")
//	private Integer cumActUsu;

}
