package negrosa.references.exception;

public class EmptyFileException extends RuntimeException {
	
	public EmptyFileException() {
		super("file should not be empty");
	}
	
	public EmptyFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	public EmptyFileException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public EmptyFileException(String message) {
		super(message);
	}
	
	public EmptyFileException(Throwable cause) {
		super(cause);
	}
	
}