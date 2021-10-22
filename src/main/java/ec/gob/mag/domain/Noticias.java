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

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@ToString(of = "notId")
@EqualsAndHashCode(of = "notId")
@Builder

@Data
@Entity
@Table(name = "noticias", schema = "sc_noticias_mag")
public class Noticias implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla", required = true, readOnly = true)
	@Column(name = "not_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("notId")
	private Long notId;

	@ApiModelProperty(value = "Ejemplo parametro String", example = "Titulo noticia")
	@Column(name = "not_titulo")
	@JsonProperty("notTitulo")
	@JsonInclude(Include.NON_NULL)
	private String notTitulo;

	@ApiModelProperty(value = "Ejemplo parametro String", example = "Titulo noticia")
	@Column(name = "not_descripcion")
	@JsonProperty("notDescripcion")
	@JsonInclude(Include.NON_NULL)
	private String notDescripcion;

	@ApiModelProperty(value = "Campo para guardar la imagen")
	@Column(name = "not_imagen")
	@JsonProperty("notImagen")
	@JsonInclude(Include.NON_NULL)
	private String notImagen;

	@ApiModelProperty(value = "Este campo almacena los valores f =false para eliminado logico  y t= true para indicar que está activo", required = true, allowableValues = "false=>no eliminado lógico, true=> eliminado lógico", example = "")
	@Column(name = "not_estado_publi", columnDefinition = "boolean default false")
	@JsonProperty("notEstadoPubli")
	@JsonInclude(Include.NON_NULL)
	private Boolean notEstadoPubli;

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
	@JsonProperty("area")
//	@JsonInclude(Include.NON_NULL)
	@JsonBackReference
	private Area area;

	/*****************************************************
	 * SECCION - CAMPOS POR DEFECTO EN TODAS LAS ENTIDADES
	 *****************************************************/
	@ApiModelProperty(value = "11=activo  12=inactivo", required = true, allowableValues = "11=>activo, 12=>inactivo", example = "11")
	@Column(name = "not_estado", columnDefinition = "Integer default 11")
	@JsonProperty("notEstado")
	@JsonInclude(Include.NON_NULL)
	private Integer notEstado;

	@ApiModelProperty(value = "Fecha de registro del campo", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "not_reg_fecha", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@JsonProperty("notRegFecha")
	@JsonInclude(Include.NON_NULL)
	private Date notRegFecha;

	@ApiModelProperty(value = "Id de usuario que creó el regristro", example = "")
	@Column(name = "not_reg_usu", nullable = false)
	@JsonProperty("notRegUsu")
	@JsonInclude(Include.NON_NULL)
	private Integer notRegUsu;

	@ApiModelProperty(value = "Fecha en la que hizo la actualización del registro", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "not_act_fecha")
	@JsonProperty("notActFecha")
	@JsonInclude(Include.NON_NULL)
	private Date notActFecha;

	@ApiModelProperty(value = "Id de usuario que actualizacio del campo", example = "")
	@Column(name = "not_act_usu")
	@JsonProperty("notActUsu")
	private Integer notActUsu;

	@ApiModelProperty(value = "Este campo almacena los valores f =false para eliminado logico  y t= true para indicar que está activo", required = true, allowableValues = "false=>no eliminado lógico, true=> eliminado lógico", example = "")
	@Column(name = "not_eliminado", columnDefinition = "boolean default false")
	@JsonProperty("cEliminado")
	@JsonInclude(Include.NON_NULL)
	private Boolean notEliminado;

	@PrePersist
	void prePersist() {
		this.notEstado = 11;
		this.notEliminado = false;
		this.notRegFecha = new Date();
	}

	@PreUpdate
	void preUpdate() {
		this.notActFecha = new Date();
	}
}
