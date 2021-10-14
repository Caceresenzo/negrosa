package negrosa.motd.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import negrosa.backend.exception.EntityNotFoundException;
import negrosa.motd.dto.request.PresentationUpdateRequest;
import negrosa.motd.entity.Presentation;
import negrosa.motd.entity.Slide;
import negrosa.motd.repository.PresentationRepository;
import negrosa.motd.service.PresentationService;
import negrosa.motd.service.SlideService;

@Service
public class PresentationServiceImpl implements PresentationService {
	
	public static final String ENTITY = "presentation";
	public static final String FIELD_ACTIVE = "active";
	
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
	public Presentation byActive() {
		return repository
				.findFirstByActiveTrue()
				.orElseThrow(() -> EntityNotFoundException.format(getEntityName(), FIELD_ACTIVE, true));
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
	public Presentation update(Presentation presentation, PresentationUpdateRequest body) {
		boolean needSave = false;
		
		if (body.getName() != null && !Objects.equals(presentation.getName(), body.getName())) {
			needSave = true;
			
			presentation.setName(body.getName());
		}
		
		if (body.getActive() != null && presentation.isActive() != body.getActive()) {
			needSave = true;
			
			boolean newValue = body.getActive();
			if (newValue) {
				disableCurrentlyActives();
			}
			
			presentation.setActive(newValue);
		}
		
		if (body.getSlideDuration() != null && presentation.getSlideDuration() != body.getSlideDuration()) {
			needSave = true;
			
			presentation.setSlideDuration(body.getSlideDuration());
		}
		
		if (needSave) {
			return repository.save(presentation);
		}
		
		return presentation;
	}
	
	@Override
	@Transactional
	public void delete(Presentation presentation) {
		slideService.delete(presentation);
		repository.delete(presentation);
	}
	
	@Override
	public Presentation touch(Presentation presentation) {
		return repository.save(presentation.setUpdatedAt(LocalDateTime.now()));
	}
	
	private void disableCurrentlyActives() {
		/* should not have more than one, but just in case */
		List<Presentation> actives = repository.findAllByActiveTrue();
		
		actives.forEach((presentation) -> presentation.setActive(false));
		
		repository.saveAll(actives);
	}
	
	@Override
	public String getEntityName() {
		return ENTITY;
	}
	
}