package todo1.com.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExceptionResponse {
	private HttpStatus status;
	private Date timestamp;
	private String message;

	@JsonInclude(Include.NON_NULL)
	private String details;

	@JsonInclude(Include.NON_NULL)
	private String detailsJson;

	@JsonInclude(Include.NON_NULL)
	private String proyect;

	@JsonInclude(Include.NON_NULL)
	private String classe;

	@JsonInclude(Include.NON_NULL)
	private String method;

	@JsonInclude(Include.NON_NULL)
	private EnumCodeExceptions numberCode;

	@JsonInclude(Include.NON_NULL)
	private EnumTypeExceptions type;

//	@JsonInclude(Include.NON_NULL)
//	private String stack;

}
