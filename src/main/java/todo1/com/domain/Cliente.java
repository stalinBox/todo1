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
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = "cli_id")
@EqualsAndHashCode(of = "cli_id")
@Builder

@Data
@Entity
@Table(name = "cliente", schema = "sc_kardex")
public class Cliente {

	@Id
	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla", required = true, readOnly = true)
	@Column(name = "cli_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("cli_id")
	private Long cli_id;

	@ApiModelProperty(value = "Nombre del proveedor", example = "Proveedor ABC")
	@Column(name = "cli_nombre")
	@JsonProperty("cli_nombre")
	@JsonInclude(Include.NON_NULL)
	private String cli_nombre;

	@ApiModelProperty(value = "Apellido del proveedor", example = "Proveedor ABC")
	@Column(name = "cli_apellido")
	@JsonProperty("cli_apellido")
	@JsonInclude(Include.NON_NULL)
	private String cli_apellido;

	@ApiModelProperty(value = "Direccion del proveedor", example = "Dir ABC")
	@Column(name = "cli_dir")
	@JsonProperty("cli_dir")
	@JsonInclude(Include.NON_NULL)
	private String cli_dir;

	@ApiModelProperty(value = "Telefono de proveedor", example = "0000000000")
	@Column(name = "cli_telf")
	@JsonProperty("cli_telf")
	@JsonInclude(Include.NON_NULL)
	private String cli_telf;

	/*****************************************************
	 * SECCION - CAMPOS AUDIT
	 *****************************************************/
	@ApiModelProperty(value = "Estado del registro", example = "0000000000")
	@Column(name = "cli_estado")
	@JsonProperty("cli_estado")
	@JsonInclude(Include.NON_NULL)
	private Boolean cli_estado;

	@ApiModelProperty(value = "Fecha del registro", example = "0000000000")
	@Column(name = "cli_created")
	@JsonProperty("cli_created")
	@JsonInclude(Include.NON_NULL)
	private Date cli_created;

	/******************************************************
	 * SECCION - RELACIONES JPA
	 ******************************************************/
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente", cascade = { CascadeType.ALL })
	@JsonProperty("venta")
	private List<Venta> venta;

	@PrePersist
	void prePersist() {
		this.cli_estado = true;
		this.cli_created = new Date();
	}

}
