package negrosa.backend.service.base;

import java.util.Optional;

import negrosa.backend.exception.EntityNotFoundException;

public interface ByIdBaseService<T> extends BaseEntityService {
	
	public static final String FIELD_ID = "id";
	
	default T byId(long id) {
		return findById(id).orElseThrow(() -> EntityNotFoundException.format(getEntityName(), FIELD_ID, id));
	}
	
	Optional<T> findById(long id);
	
}