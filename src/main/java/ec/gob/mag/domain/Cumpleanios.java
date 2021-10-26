package ec.gob.mag.domain;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = "cumId")
@EqualsAndHashCode(of = "cumId")
@Builder

@Data
@Entity
@Table(name = "cumpleanios", schema = "sc_noticias_mag")
public class Cumpleanios implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla", required = true, readOnly = true)
	@Column(name = "cum_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("cumId")
	private Long cumId;

	@ApiModelProperty(value = "Ejemplo parametro String", example = "Nombres Completos")
	@Size(min = 0, max = 64, message = "_error.validation_range.message-[0, 64]")
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

	/******************************************************
	 * SECCION - RELACIONES JPA
	 ******************************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "are_id")
	@JsonProperty("area")
	private Area area;

	/*****************************************************
	 * SECCION - CAMPOS POR DEFECTO EN TODAS LAS ENTIDADES
	 *****************************************************/
	@ApiModelProperty(value = "11=activo  12=inactivo", required = true, allowableValues = "11=>activo, 12=>inactivo", example = "11")
	@Column(name = "cum_estado", columnDefinition = "Integer default 11")
	@JsonProperty("cumEstado")
	@JsonInclude(Include.NON_NULL)
	private Integer cumEstado;

	@ApiModelProperty(value = "Fecha de registro del campo", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cum_reg_fecha", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@JsonProperty("cumRegFecha")
	@JsonInclude(Include.NON_NULL)
	private Date cumRegFecha;

	@ApiModelProperty(value = "Id de usuario que creó el regristro", example = "")
	@Column(name = "cum_reg_usu", nullable = false)
	@JsonProperty("cumRegUsu")
	@JsonInclude(Include.NON_NULL)
	@NotNull(message = "_error.validation_blank.message")
	private Integer cumRegUsu;

	@ApiModelProperty(value = "Fecha en la que hizo la actualización del registro", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cum_act_fecha")
	@JsonProperty("cumActFecha")
	@JsonInclude(Include.NON_NULL)
	private Date cumActFecha;

	@ApiModelProperty(value = "Id de usuario que actualizacio del campo", example = "")
	@Column(name = "cum_act_usu")
	@JsonProperty("cumActUsu")
	private Integer cumActUsu;

	@ApiModelProperty(value = "Este campo almacena los valores f =false para eliminado logico  y t= true para indicar que está activo", required = true, allowableValues = "false=>no eliminado lógico, true=> eliminado lógico", example = "")
	@Column(name = "cum_eliminado", columnDefinition = "boolean default false")
	@JsonProperty("cumEliminado")
	@JsonInclude(Include.NON_NULL)
	private Boolean cumEliminado;

	@PrePersist
	void prePersist() {
		this.cumEstado = 11;
		this.cumEliminado = false;
		this.cumRegFecha = new Date();
	}

	@PreUpdate
	void preUpdate() {
		this.cumActFecha = new Date();
	}
}
