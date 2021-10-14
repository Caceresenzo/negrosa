package negrosa.motd.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import negrosa.backend.service.base.ByIdBaseService;
import negrosa.motd.dto.request.SlideUpdateRequest;
import negrosa.motd.entity.Presentation;
import negrosa.motd.entity.Slide;

public interface SlideService extends ByIdBaseService<Slide> {

	List<Slide> create(Presentation presentation, InputStream input) throws IOException;

	Slide update(Slide slide, SlideUpdateRequest body);

	void delete(Presentation presentation);
	
}