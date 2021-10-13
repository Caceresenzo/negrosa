package negrosa.motd.dto.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PresentationCreateRequest {
	
	private double slideDuration;
	private MultipartFile file;
	
}