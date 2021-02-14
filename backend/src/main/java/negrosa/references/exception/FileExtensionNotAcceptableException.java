package negrosa.references.exception;

public class FileExtensionNotAcceptableException extends RuntimeException {
	
	public FileExtensionNotAcceptableException() {
		super("file extension not acceptable");
	}
	
	public FileExtensionNotAcceptableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	public FileExtensionNotAcceptableException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public FileExtensionNotAcceptableException(String message) {
		super(message);
	}
	
	public FileExtensionNotAcceptableException(Throwable cause) {
		super(cause);
	}
	
}