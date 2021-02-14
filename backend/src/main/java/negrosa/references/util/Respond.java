package negrosa.references.util;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;

import negrosa.backend.model.api.AbstractApiResponse;
import negrosa.backend.model.api.impl.ApiAnwser;
import negrosa.backend.model.api.impl.ApiError;
import negrosa.common.util.UnsafeSupplier;
import negrosa.references.exception.EmptyFileException;
import negrosa.references.exception.FileExtensionNotAcceptableException;
import negrosa.references.exception.ReferenceFoundException;
import negrosa.references.exception.ReferenceNotFoundException;

public class Respond {
	
	public static ResponseEntity<?> auto(UnsafeSupplier<?> action) {
		return (ResponseEntity<?>) auto(action, true);
	}
	
	public static Object auto(UnsafeSupplier<?> action, boolean toEntityResponse) {
		try {
			Object returned = action.get();
			
			if (returned instanceof MappingJacksonValue) {
				MappingJacksonValue value = (MappingJacksonValue) returned;
				value.setValue(new ApiAnwser<>(value.getValue()));
				
				return value;
			}
			
			if (returned instanceof AbstractApiResponse) {
				return ((AbstractApiResponse) returned).toResponseEntity();
			}
			
			return new ApiAnwser<>(returned).toResponseEntity();
		} catch (EmptyFileException exception) {
			return error(exception, HttpStatus.BAD_REQUEST);
		} catch (FileExtensionNotAcceptableException exception) { 
			return error(exception, HttpStatus.NOT_ACCEPTABLE);
		} catch (ReferenceNotFoundException exception) { 
			return error(exception, HttpStatus.NOT_FOUND);
		} catch (ReferenceFoundException exception) { 
			return error(exception, HttpStatus.CONFLICT);
		} catch (Throwable exception) {
			return error(exception, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private static ResponseEntity<?> error(Throwable exception, HttpStatus status) {
		return new ApiError(status)
				.setMessage(exception.getMessage())
				.setDebugMessage(ExceptionUtils.getStackTrace(exception))
				.toResponseEntity();
	}
	
}