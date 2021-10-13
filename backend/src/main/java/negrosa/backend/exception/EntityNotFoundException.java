package negrosa.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {
	
	public EntityNotFoundException() {
		super("entity not found");
	}
	
	public EntityNotFoundException(String message) {
		super(message);
	}
	
	public static EntityNotFoundException format(String entity, String word) {
		return new EntityNotFoundException(String.format("no %s %s found", word, entity));
	}
	
	public static EntityNotFoundException format(String entity, String key, Object value) {
		return new EntityNotFoundException(String.format("no %s found with %s = `%s`", entity, key, value));
	}
	
	public static EntityNotFoundException format2(String entity, String key1, Object value1, String key2, Object value2) {
		return new EntityNotFoundException(String.format("no %s found with %s = `%s` and %s = `%s`", entity, key1, value1, key2, value2));
	}
	
}