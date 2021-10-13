package negrosa.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.CONFLICT)
public class EntityExistsException extends RuntimeException {
	
	public EntityExistsException() {
		super("entity exists");
	}
	
}