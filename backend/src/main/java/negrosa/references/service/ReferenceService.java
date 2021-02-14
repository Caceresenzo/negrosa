package negrosa.references.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ReferenceService {
	
	List<String> all();

	InputStream find(String name);

	String store(MultipartFile file) throws IOException, InterruptedException;

	void delete(String name) throws IOException;
	
}