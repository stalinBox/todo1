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
@ToString(of = "prove_id")
@EqualsAndHashCode(of = "prove_id")
@Builder

@Data
@Entity
@Table(name = "proveedor", schema = "sc_kardex")
public class Proveedor {

	@Id
	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla", required = true, readOnly = true)
	@Column(name = "prove_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("prove_id")
	private Long prove_id;

	@ApiModelProperty(value = "Nombre del proveedor", example = "Proveedor ABC")
	@Column(name = "prove_nombre")
	@JsonProperty("prove_nombre")
	@JsonInclude(Include.NON_NULL)
	private String prove_nombre;

	@ApiModelProperty(value = "Direccion del proveedor", example = "Dir ABC")
	@Column(name = "prove_dir")
	@JsonProperty("prove_dir")
	@JsonInclude(Include.NON_NULL)
	private String prove_dir;

	@ApiModelProperty(value = "Telefono de proveedor", example = "0000000000")
	@Column(name = "prove_telf")
	@JsonProperty("prove_telf")
	@JsonInclude(Include.NON_NULL)
	private String prove_telf;

	/*****************************************************
	 * SECCION - CAMPOS AUDIT
	 *****************************************************/
	@ApiModelProperty(value = "Estado del registro", example = "true")
	@Column(name = "prove_estado")
	@JsonProperty("prove_estado")
	@JsonInclude(Include.NON_NULL)
	private Boolean prove_estado;

	@ApiModelProperty(value = "Fecha del registro", example = "01/01/2022")
	@Column(name = "prove_created")
	@JsonProperty("prove_created")
	@JsonInclude(Include.NON_NULL)
	private Date prove_created;

	/******************************************************
	 * SECCION - RELACIONES JPA
	 ******************************************************/
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "proveedor", cascade = { CascadeType.ALL })
	@JsonProperty("compra")
	private List<Compra> compra;

	@PrePersist
	void prePersist() {
		this.prove_estado = true;
		this.prove_created = new Date();
	}
}
