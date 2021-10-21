package ec.gob.mag.domain.constraint;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
@ToString(of = "areId")
@EqualsAndHashCode(of = "areId")
@Builder

@Data
@Entity
public class AreaUpdate implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla", required = true, readOnly = true)
	@Column(name = "are_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("areId")
	private Long areId;

	@ApiModelProperty(value = "Ejemplo parametro String", example = "Nombres Completos")
	@Size(min = 0, max = 64, message = "_error.validation_range.message-[0, 64]")
	@Column(name = "are_nombre")
	@JsonProperty("areNombre")
	@JsonInclude(Include.NON_NULL)
	private String areNombre;

	@ApiModelProperty(value = "Ejemplo parametro String", example = "Nombres Completos")
	@Size(min = 0, max = 255, message = "_error.validation_range.message-[0, 255]")
	@Column(name = "are_descripcion")
	@JsonProperty("areDescripcion")
	@JsonInclude(Include.NON_NULL)
	private String areDescripcion;

	@ApiModelProperty(value = "Id de usuario que actualizacio del campo", example = "")
	@Column(name = "are_act_usu")
	@JsonProperty("areActUsu")
	@NotNull(message = "_error.validation_blank.message")
	private Integer areActUsu;

}
