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
@ToString(of = "comp_id")
@EqualsAndHashCode(of = "comp_id")
@Builder

@Data
@Entity
@Table(name = "compra", schema = "sc_kardex")
public class Compra {

	@Id
	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla", required = true, readOnly = true)
	@Column(name = "comp_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("comp_id")
	private Long comp_id;

	@ApiModelProperty(value = "Fecha de la compra", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "comp_fecha")
	@JsonProperty("comp_fecha")
	@JsonInclude(Include.NON_NULL)
	private Date comp_fecha;

	/*****************************************************
	 * SECCION - CAMPOS AUDIT
	 *****************************************************/
	@ApiModelProperty(value = "Estado del registro", example = "true")
	@Column(name = "comp_estado")
	@JsonProperty("comp_estado")
	@JsonInclude(Include.NON_NULL)
	private Boolean comp_estado;

	@ApiModelProperty(value = "Fecha del registro", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "comp_created")
	@JsonProperty("comp_created")
	@JsonInclude(Include.NON_NULL)
	private Date comp_created;

	/******************************************************
	 * SECCION - RELACIONES JPA
	 ******************************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonProperty("proveedor")
	@JoinColumn(name = "prove_id")
	private Proveedor proveedor;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "compra", cascade = { CascadeType.ALL })
	@JsonProperty("detalleCompra")
	private List<DetalleCompra> detalleCompra;

	@PrePersist
	void prePersist() {
		this.comp_estado = true;
		this.comp_created = new Date();
	}
}
