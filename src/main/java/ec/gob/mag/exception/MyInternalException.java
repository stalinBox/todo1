package ec.gob.mag.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MyInternalException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public MyInternalException(String message) {
		super(message);
	}
}
