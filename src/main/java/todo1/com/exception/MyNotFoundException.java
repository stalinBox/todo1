package todo1.com.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MyNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public MyNotFoundException(String message) {
		super(message);
	}
}
