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
@ToString(of = "grup_id")
@EqualsAndHashCode(of = "grup_id")
@Builder

@Data
@Entity
@Table(name = "grupo", schema = "sc_kardex")
public class Grupo {

	@Id
	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla", required = true, readOnly = true)
	@Column(name = "grup_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("grup_id")
	private Long grup_id;

	@ApiModelProperty(value = "Nombre del grupo", example = "Grupo ABC")
	@Column(name = "grup_nombre")
	@JsonProperty("grup_nombre")
	@JsonInclude(Include.NON_NULL)
	private String grup_nombre;

	/*****************************************************
	 * SECCION - CAMPOS AUDIT
	 *****************************************************/
	@ApiModelProperty(value = "Estado del registro", example = "true")
	@Column(name = "grup_estado")
	@JsonProperty("grup_estado")
	@JsonInclude(Include.NON_NULL)
	private Boolean grup_estado;

	@ApiModelProperty(value = "Fecha del registro", example = "01/01/2022")
	@Column(name = "grup_created")
	@JsonProperty("grup_created")
	@JsonInclude(Include.NON_NULL)
	private Date grup_created;

	/******************************************************
	 * SECCION - RELACIONES JPA
	 ******************************************************/
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "grupo", cascade = { CascadeType.ALL })
	@JsonProperty("subGrupo")
	@JsonInclude(Include.NON_NULL)
	private List<SubGrupo> subGrupo;

	@PrePersist
	void prePersist() {
		this.grup_estado = true;
		this.grup_created = new Date();
	}
}
