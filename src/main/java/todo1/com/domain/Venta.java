package todo1.com.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = "vent_id")
@EqualsAndHashCode(of = "vent_id")
@Builder

@Data
@Entity
@Table(name = "venta", schema = "sc_kardex")
public class Venta {

	@Id
	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla", required = true, readOnly = true)
	@Column(name = "vent_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("vent_id")
	private Long vent_id;

	@ApiModelProperty(value = "Fecha en la que hizo la actualización del registro", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "vent_fecha")
	@JsonProperty("vent_fecha")
	@JsonInclude(Include.NON_NULL)
	private Date vent_fecha;

	/*****************************************************
	 * SECCION - CAMPOS AUDIT
	 *****************************************************/
	@ApiModelProperty(value = "Estado del registro", example = "true")
	@Column(name = "vent_estado")
	@JsonProperty("vent_estado")
	@JsonInclude(Include.NON_NULL)
	private Boolean vent_estado;

	@ApiModelProperty(value = "Fecha del registro", example = "01/01/2022")
	@Column(name = "vent_created")
	@JsonProperty("vent_created")
	@JsonInclude(Include.NON_NULL)
	private Date vent_created;

	/******************************************************
	 * SECCION - RELACIONES JPA
	 ******************************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cli_id")
	@JsonProperty("cliente")
	private Cliente cliente;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "venta", cascade = { CascadeType.ALL })
	@JsonProperty("detalleCompra")
	private List<DetalleVenta> detalleVenta;

	@PrePersist
	void prePersist() {
		this.vent_estado = true;
		this.vent_created = new Date();
	}
}
