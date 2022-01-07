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
@ToString(of = "sgrup_id")
@EqualsAndHashCode(of = "sgrup_id")
@Builder

@Data
@Entity
@Table(name = "sub_grupo", schema = "sc_kardex")
public class SubGrupo {

	@Id
	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla", required = true, readOnly = true)
	@Column(name = "sgrup_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("sgrup_id")
	private Long sgrup_id;

	@ApiModelProperty(value = "Nombre del sub grupo", example = "sub grupo ABC")
	@Column(name = "sgrup_nombre")
	@JsonProperty("sgrup_nombre")
	@JsonInclude(Include.NON_NULL)
	private String sgrup_nombre;

	/*****************************************************
	 * SECCION - CAMPOS AUDIT
	 *****************************************************/
	@ApiModelProperty(value = "Estado del registro", example = "true")
	@Column(name = "sgrup_estado")
	@JsonProperty("sgrup_estado")
	@JsonInclude(Include.NON_NULL)
	private Boolean sgrup_estado;

	@ApiModelProperty(value = "Fecha del registro", example = "01/01/2022")
	@Column(name = "sgrup_created")
	@JsonProperty("sgrup_created")
	@JsonInclude(Include.NON_NULL)
	private Date sgrup_created;

	/******************************************************
	 * SECCION - RELACIONES JPA
	 ******************************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "grup_id")
	@JsonProperty("grupo")
	private Grupo grupo;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "subGrupo", cascade = { CascadeType.ALL })
	@JsonProperty("producto")
//	@JsonBackReference
	private List<Producto> producto;

	@PrePersist
	void prePersist() {
		this.sgrup_estado = true;
		this.sgrup_created = new Date();
	}
}
