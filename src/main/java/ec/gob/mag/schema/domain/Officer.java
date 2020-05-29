package ec.gob.mag.schema.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@Builder

@Data
@Entity
//@Table(name = "tbl_agencia", schema = "sc_visitas")
public class Officer {

	@Id
	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla", required = true, readOnly = true)
	@Column(name = "campo_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("id")
	private Long id;

	@ApiModelProperty(notes = "Provide the email of the officer", required = true)
	@Email(message = "_error.validation_valid_mail.message")
	@NotBlank(message = "_error.validation_blank.message")
	private String email;

	@ApiModelProperty(value = "Ejemplo parametro String", example = "día, hora, si/no")
	@Size(min = 0, max = 64, message = "_error.validation_range.message-[0, 64]")
	@Column(name = "campo_valor", nullable = false)
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("campoString")
	@JsonInclude(Include.NON_NULL)
	private String campoString;

	@ApiModelProperty(value = "Ejemplo parametros Long, Integer, Double", example = "Tipo de dato a ingresar")
	@Column(name = "campo_tipo_dato", nullable = false)
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("campoLong")
	@JsonInclude(Include.NON_NULL)
	private Long campoLong;

	/******************************************************
	 * SECCION - RELACIONES JPA
	 ******************************************************/

	/*****************************************************
	 * SECCION - CAMPOS POR DEFECTO EN TODAS LAS ENTIDADES
	 *****************************************************/
	@ApiModelProperty(value = "11=activo  12=inactivo", required = true, allowableValues = "11=>activo, 12=>inactivo", example = "11")
	@Column(name = "campo_estado", columnDefinition = "Integer default 11")
	@JsonProperty("campoEstado")
	@JsonInclude(Include.NON_NULL)
	private Integer campoEstado;

	@ApiModelProperty(value = "Fecha de registro del campo", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "campo_reg_fecha", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@JsonProperty("campoRegFecha")
	@JsonInclude(Include.NON_NULL)
	private Date campoRegFecha;

	@ApiModelProperty(value = "Id de usuario que creó el regristro", example = "")
	@Column(name = "campo_reg_usu", nullable = false)
	@JsonProperty("campoRegUsu")
	@JsonInclude(Include.NON_NULL)
	private Long campoRegUsu;

	@ApiModelProperty(value = "Fecha en la que hizo la actualización del registro", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "campo_act_fecha")
	@JsonProperty("campoActFecha")
	@JsonInclude(Include.NON_NULL)
	private Date campoActFecha;

	@ApiModelProperty(value = "Id de usuario que actualizacio del campo", example = "")
	@Column(name = "campo_act_usu")
	@JsonProperty("campoActUsu")
	private Long campoActUsu;

	@ApiModelProperty(value = "Este campo almacena los valores f =false para eliminado logico  y t= true para indicar que está activo", required = true, allowableValues = "false=>no eliminado lógico, true=> eliminado lógico", example = "")
	@Column(name = "campo_eliminado", columnDefinition = "boolean default false")
	@JsonProperty("campoEliminado")
	@JsonInclude(Include.NON_NULL)
	private Boolean campoEliminado;

	@PrePersist
	void prePersist() {
		this.campoEstado = 11;
		this.campoEliminado = false;
		this.campoRegFecha = new Date();
	}

	@PreUpdate
	void preUpdate() {
		this.campoActFecha = new Date();
	}
}
