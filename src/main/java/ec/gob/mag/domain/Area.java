package ec.gob.mag.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
@ToString(of = "areId")
@EqualsAndHashCode(of = "areId")
@Builder

@Data
@Entity
@Table(name = "area", schema = "sc_noticias_mag")
public class Area implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla", required = true, readOnly = true)
	@Column(name = "are_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("areId")
	private Long areId;

	@ApiModelProperty(value = "Ejemplo parametro String", example = "Nombres Completos")
//	@Size(min = 0, max = 64, message = "_error.validation_range.message-[0, 64]")
	@Column(name = "are_nombre")
//	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("areNombre")
	@JsonInclude(Include.NON_NULL)
	private String areNombre;

	@ApiModelProperty(value = "Ejemplo parametro String", example = "Nombres Completos")
//	@Size(min = 0, max = 64, message = "_error.validation_range.message-[0, 64]")
	@Column(name = "are_descripcion")
//	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("areDescripcion")
	@JsonInclude(Include.NON_NULL)
	private String areDescripcion;

	/******************************************************
	 * SECCION - RELACIONES JPA
	 ******************************************************/

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "cum_id")
	@JsonProperty("cumpleanios")
	@JsonInclude(Include.NON_NULL)
	private List<Cumpleanios> cumpleanios;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "not_id")
	@JsonProperty("noticias")
	@JsonInclude(Include.NON_NULL)
	private List<Noticias> noticias;

	/*****************************************************
	 * SECCION - CAMPOS POR DEFECTO EN TODAS LAS ENTIDADES
	 *****************************************************/
	@ApiModelProperty(value = "11=activo  12=inactivo", required = true, allowableValues = "11=>activo, 12=>inactivo", example = "11")
	@Column(name = "are_estado", columnDefinition = "Integer default 11")
	@JsonProperty("areEstado")
	@JsonInclude(Include.NON_NULL)
	private Integer areEstado;

	@ApiModelProperty(value = "Fecha de registro del campo", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "are_reg_fecha", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@JsonProperty("areRegFecha")
	@JsonInclude(Include.NON_NULL)
	private Date areRegFecha;

	@ApiModelProperty(value = "Id de usuario que creó el regristro", example = "")
	@Column(name = "are_reg_usu", nullable = false)
	@JsonProperty("areRegUsu")
	@JsonInclude(Include.NON_NULL)
	private Integer areRegUsu;

	@ApiModelProperty(value = "Fecha en la que hizo la actualización del registro", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "are_act_fecha")
	@JsonProperty("areActFecha")
	@JsonInclude(Include.NON_NULL)
	private Date areActFecha;

	@ApiModelProperty(value = "Id de usuario que actualizacio del campo", example = "")
	@Column(name = "are_act_usu")
	@JsonProperty("areActUsu")
	private Integer areActUsu;

	@ApiModelProperty(value = "Este campo almacena los valores f =false para eliminado logico  y t= true para indicar que está activo", required = true, allowableValues = "false=>no eliminado lógico, true=> eliminado lógico", example = "")
	@Column(name = "are_eliminado", columnDefinition = "boolean default false")
	@JsonProperty("areEliminado")
	@JsonInclude(Include.NON_NULL)
	private Boolean areEliminado;

	@PrePersist
	void prePersist() {
		this.areEstado = 11;
		this.areEliminado = false;
		this.areRegFecha = new Date();
	}

	@PreUpdate
	void preUpdate() {
		this.areActFecha = new Date();
	}
}
