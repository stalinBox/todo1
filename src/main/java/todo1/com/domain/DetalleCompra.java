package todo1.com.domain;

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
import javax.persistence.Table;

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
@ToString(of = "detc_id")
@EqualsAndHashCode(of = "detc_id")
@Builder

@Data
@Entity
@Table(name = "detalle_compra", schema = "sc_kardex")
public class DetalleCompra {

	@Id
	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla", required = true, readOnly = true)
	@Column(name = "detc_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("detc_id")
	private Long detc_id;

	@ApiModelProperty(value = "Cantidad de la compra", example = "0")
	@Column(name = "detc_cantidad")
	@JsonProperty("detc_cantidad")
	@JsonInclude(Include.NON_NULL)
	private Integer detc_cantidad;

	@ApiModelProperty(value = "Cantidad del descuento si existiera", example = "00")
	@Column(name = "detc_descuento")
	@JsonProperty("detc_descuento")
	@JsonInclude(Include.NON_NULL)
	private Integer detc_descuento;

	/*****************************************************
	 * SECCION - CAMPOS AUDIT
	 *****************************************************/
	@ApiModelProperty(value = "Estado del registro", example = "true")
	@Column(name = "detc_estado")
	@JsonProperty("detc_estado")
	@JsonInclude(Include.NON_NULL)
	private Boolean detc_estado;

	@ApiModelProperty(value = "Fecha del registro", example = "01/01/2022")
	@Column(name = "detc_created")
	@JsonProperty("detc_created")
	@JsonInclude(Include.NON_NULL)
	private Date detc_created;

	/******************************************************
	 * SECCION - RELACIONES JPA
	 ******************************************************/

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "comp_id")
	@JsonProperty("compra")
	@JsonInclude(Include.NON_NULL)
	private Compra compra;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prod_id")
	@JsonProperty("producto")
	@JsonInclude(Include.NON_NULL)
	private Producto producto;

	@PrePersist
	void prePersist() {
		this.detc_estado = true;
		this.detc_created = new Date();
	}
}
