package negrosa.motd.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import negrosa.motd.entity.Presentation;
import negrosa.motd.entity.Slide;
import negrosa.motd.repository.PresentationRepository;
import negrosa.motd.service.PresentationService;
import negrosa.motd.service.SlideService;

@Service
public class PresentationServiceImpl implements PresentationService {
	
	public static final String ENTITY = "presentation";
	
	@Autowired
	private PresentationRepository repository;
	
	@Autowired
	private SlideService slideService;
	
	@Override
	public Optional<Presentation> findById(long id) {
		return repository.findById(id);
	}
	
	@Override
	public List<Presentation> findAll() {
		return repository.findAll();
	}
	
	@Override
	@Transactional
	public Presentation create(MultipartFile file) throws IOException {
		Presentation presentation = repository.save(new Presentation()
				.setName(file.getOriginalFilename()));
		
		try (InputStream input = new ByteArrayInputStream(file.getBytes())) {
			List<Slide> slides = slideService.create(presentation, input);
			
			return presentation.setSlides(slides);
		}
	}
	
	@Override
	public String getEntityName() {
		return ENTITY;
	}
	
}