package ec.gob.mag.domain;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@Builder

@Data
@Entity
@Table(name = "tbl_template", schema = "public")
public class Template implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla", required = true, readOnly = true)
	@Column(name = "tmp_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("id")
	private Long id;

	@ApiModelProperty(notes = "Provide the email of the officer", required = true)
	@Email(message = "_error.validation_valid_mail.message")
	@Column(name = "tmp_mail")
	@NotBlank(message = "_error.validation_blank.message")
	@JsonProperty("tmp_mail")
	private String tmpMail;

	@ApiModelProperty(value = "Ejemplo parametro String", example = "Cualquier string")
	@Size(min = 0, max = 64, message = "_error.validation_range.message-[0, 64]")
	@Column(name = "tmp_str")
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("tmpStr")
	@JsonInclude(Include.NON_NULL)
	private String tmpStr;

	@ApiModelProperty(value = "Ejemplo parametros Long, Integer, Double")
	@Column(name = "tmp_int")
	@NotNull(message = "_error.validation_blank.message")
	@JsonProperty("tmpInt")
	@JsonInclude(Include.NON_NULL)
	private Integer tmpInt;

	/******************************************************
	 * SECCION - RELACIONES JPA
	 ******************************************************/

	/*****************************************************
	 * SECCION - CAMPOS POR DEFECTO EN TODAS LAS ENTIDADES
	 *****************************************************/
	@ApiModelProperty(value = "11=activo  12=inactivo", required = true, allowableValues = "11=>activo, 12=>inactivo", example = "11")
	@Column(name = "tmp_estado", columnDefinition = "Integer default 11")
	@JsonProperty("tmpEstado")
	@JsonInclude(Include.NON_NULL)
	private Integer tmpEstado;

	@ApiModelProperty(value = "Fecha de registro del campo", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "tmp_reg_fecha", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@JsonProperty("tmpRegFecha")
	@JsonInclude(Include.NON_NULL)
	private Date tmpRegFecha;

	@ApiModelProperty(value = "Id de usuario que creó el regristro", example = "")
	@Column(name = "tmp_reg_usu", nullable = false)
	@JsonProperty("tmpRegUsu")
	@JsonInclude(Include.NON_NULL)
	@NotNull(message = "_error.validation_blank.message")
	private Long tmpRegUsu;

	@ApiModelProperty(value = "Fecha en la que hizo la actualización del registro", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "tmp_act_fecha")
	@JsonProperty("tmpActFecha")
	@JsonInclude(Include.NON_NULL)
	private Date tmpActFecha;

	@ApiModelProperty(value = "Id de usuario que actualizacio del campo", example = "")
	@Column(name = "tmp_act_usu")
	@JsonProperty("tmpActUsu")
	private Long tmpActUsu;

	@ApiModelProperty(value = "Este campo almacena los valores f =false para eliminado logico  y t= true para indicar que está activo", required = true, allowableValues = "false=>no eliminado lógico, true=> eliminado lógico", example = "")
	@Column(name = "tmp_eliminado", columnDefinition = "boolean default false")
	@JsonProperty("tmpEliminado")
	@JsonInclude(Include.NON_NULL)
	private Boolean tmpEliminado;

	@PrePersist
	void prePersist() {
		this.tmpEstado = 11;
		this.tmpEliminado = false;
		this.tmpRegFecha = new Date();
	}

	@PreUpdate
	void preUpdate() {
		this.tmpActFecha = new Date();
	}
}
