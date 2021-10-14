package negrosa.motd.controller.presentation;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import negrosa.motd.dto.request.PresentationUpdateRequest;
import negrosa.motd.entity.Presentation;
import negrosa.motd.entity.Slide;
import negrosa.motd.service.PresentationService;

public abstract class BasePresentationController {
	
	@Autowired
	protected PresentationService presentationService;
	
	public List<Presentation> index() {
		return presentationService.findAll();
	}
	
	public Presentation create(MultipartFile file) throws IOException {
		return presentationService.create(file);
	}
	
	public Presentation show(Presentation presentation) {
		return presentation;
	}
	
	public Presentation update(Presentation presentation, PresentationUpdateRequest body) {
		return presentationService.update(presentation, body);
	}
	
	public List<Slide> showSlides(Presentation presentation) {
		return presentation.getSlides();
	}
	
}