package negrosa.references.exception;

public class ConversionFailedException extends RuntimeException {
	
	public ConversionFailedException() {
		super("conversion failed");
	}
	
	public ConversionFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	public ConversionFailedException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ConversionFailedException(String message) {
		super(message);
	}
	
	public ConversionFailedException(Throwable cause) {
		super(cause);
	}
	
}