package negrosa.references.exception;

public class ReferenceFoundException extends RuntimeException {
	
	public ReferenceFoundException() {
		super("reference found");
	}
	
	public ReferenceFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	public ReferenceFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ReferenceFoundException(String message) {
		super(message);
	}
	
	public ReferenceFoundException(Throwable cause) {
		super(cause);
	}
	
}