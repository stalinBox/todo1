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
@ToString(of = "detv_id")
@EqualsAndHashCode(of = "detv_id")
@Builder

@Data
@Entity
@Table(name = "detalle_venta", schema = "sc_kardex")
public class DetalleVenta {

	@Id
	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla", required = true, readOnly = true)
	@Column(name = "detv_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("detv_id")
	private Long detv_id;

	@ApiModelProperty(value = "Descripcion de la venta", example = "Alguna descripcion")
	@Column(name = "detv_desc")
	@JsonProperty("detv_desc")
	@JsonInclude(Include.NON_NULL)
	private String detv_desc;

	@ApiModelProperty(value = "Cantidad de la compra", example = "0")
	@Column(name = "detv_cantidad")
	@JsonProperty("detv_cantidad")
	@JsonInclude(Include.NON_NULL)
	private Integer detv_cantidad;

	@ApiModelProperty(value = "Porcentaje del Iva", example = "0")
	@Column(name = "detv_iva")
	@JsonProperty("detv_iva")
	@JsonInclude(Include.NON_NULL)
	private Integer detv_iva;

	/*****************************************************
	 * SECCION - CAMPOS AUDIT
	 *****************************************************/
	@ApiModelProperty(value = "Estado del registro", example = "true")
	@Column(name = "detv_estado")
	@JsonProperty("detv_estado")
	@JsonInclude(Include.NON_NULL)
	private Boolean detv_estado;

	@ApiModelProperty(value = "Fecha del registro", example = "01/01/2022")
	@Column(name = "detv_created")
	@JsonProperty("detv_created")
	@JsonInclude(Include.NON_NULL)
	private Date detv_created;

	/******************************************************
	 * SECCION - RELACIONES JPA
	 ******************************************************/

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vent_id")
	@JsonProperty("venta")
	@JsonInclude(Include.NON_NULL)
	private Venta venta;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prod_id")
	@JsonProperty("producto")
	@JsonInclude(Include.NON_NULL)
	private Producto producto;

	@PrePersist
	void prePersist() {
		this.detv_estado = true;
		this.detv_created = new Date();
	}
}
