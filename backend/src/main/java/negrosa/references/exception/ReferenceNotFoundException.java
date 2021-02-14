package negrosa.references.exception;

public class ReferenceNotFoundException extends RuntimeException {
	
	public ReferenceNotFoundException() {
		super("reference not found");
	}
	
	public ReferenceNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	public ReferenceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ReferenceNotFoundException(String message) {
		super(message);
	}
	
	public ReferenceNotFoundException(Throwable cause) {
		super(cause);
	}
	
}