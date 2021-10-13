package negrosa.motd.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import negrosa.backend.service.base.ByIdBaseService;
import negrosa.motd.entity.Presentation;

public interface PresentationService extends ByIdBaseService<Presentation> {

	List<Presentation> findAll();

	Presentation create(MultipartFile file) throws IOException;
	
}