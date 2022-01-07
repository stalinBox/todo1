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
@ToString(of = "prod_id")
@EqualsAndHashCode(of = "prod_id")
@Builder

@Data
@Entity
@Table(name = "producto", schema = "sc_kardex")
public class Producto {

	@Id
	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla", required = true, readOnly = true)
	@Column(name = "")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("prod_id")
	private Long prod_id;

	@ApiModelProperty(value = "Nombre del producto", example = "Producto ABC")
	@Column(name = "prod_nombre")
	@JsonProperty("prod_nombre")
	@JsonInclude(Include.NON_NULL)
	private String prod_nombre;

	@ApiModelProperty(value = "Descripcion de producto", example = "Producto comestible")
	@Column(name = "prod_desc")
	@JsonProperty("prod_desc")
	@JsonInclude(Include.NON_NULL)
	private String prod_desc;

	@ApiModelProperty(value = "Precio compra producto", example = "00.00")
	@Column(name = "prod_pc")
	@JsonProperty("prod_pc")
	@JsonInclude(Include.NON_NULL)
	private Double prod_pc;

	@ApiModelProperty(value = "Precio venta al publico producto", example = "00.00")
	@Column(name = "prod_pv")
	@JsonProperty("prod_pv")
	@JsonInclude(Include.NON_NULL)
	private Double prod_pv;

	@ApiModelProperty(value = "Stock del producto", example = "1")
	@Column(name = "prod_stock")
	@JsonProperty("prod_stock")
	@JsonInclude(Include.NON_NULL)
	private Integer prod_stock;

	/*****************************************************
	 * SECCION - CAMPOS AUDIT
	 *****************************************************/
	@ApiModelProperty(value = "Estado del registro", example = "true")
	@Column(name = "prod_estado")
	@JsonProperty("prod_estado")
	@JsonInclude(Include.NON_NULL)
	private Boolean prod_estado;

	@ApiModelProperty(value = "Fecha del registro", example = "01/01/2022")
	@Column(name = "prod_created")
	@JsonProperty("prod_created")
	@JsonInclude(Include.NON_NULL)
	private Date prod_created;

	/******************************************************
	 * SECCION - RELACIONES JPA
	 ******************************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sgrup_id")
	@JsonProperty("subGrupo")
	private SubGrupo subGrupo;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "producto", cascade = { CascadeType.ALL })
	@JsonProperty("detalleCompra")
	private List<DetalleCompra> detalleCompra;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "producto", cascade = { CascadeType.ALL })
	@JsonProperty("detalleVenta")
	private List<DetalleVenta> detalleVenta;

	@PrePersist
	void prePersist() {
		this.prod_estado = true;
		this.prod_created = new Date();
	}
}
