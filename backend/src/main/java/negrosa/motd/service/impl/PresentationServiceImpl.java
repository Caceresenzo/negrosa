package negrosa.motd.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.extern.slf4j.Slf4j;
import negrosa.backend.exception.EntityNotFoundException;
import negrosa.motd.dto.request.PresentationUpdateRequest;
import negrosa.motd.entity.Presentation;
import negrosa.motd.entity.Slide;
import negrosa.motd.repository.PresentationRepository;
import negrosa.motd.service.PresentationService;
import negrosa.motd.service.SlideService;

@Service
@Slf4j
public class PresentationServiceImpl implements PresentationService, DisposableBean {
	
	public static final String ENTITY = "presentation";
	public static final String FIELD_ACTIVE = "active";
	
	public static final long EMITTER_TIMEOUT = Duration.ofMinutes(2).toSeconds();
	public static final String EVENT_NOTIFY_UPDATE = "notify_presentation_update";
	
	@Autowired
	private PresentationRepository repository;
	
	@Autowired
	private SlideService slideService;
	
	private List<SseEmitter> emitters = Collections.synchronizedList(new ArrayList<>(1));
	
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
			} else {
				notifyNoActive();
			}
			
			presentation.setActive(newValue);
		}
		
		if (body.getSlideDuration() != null && presentation.getSlideDuration() != body.getSlideDuration()) {
			needSave = true;
			
			presentation.setSlideDuration(body.getSlideDuration());
		}
		
		if (needSave) {
			if (presentation.isActive()) {
				notifyActive(presentation);
			}
			
			return repository.save(presentation);
		}
		
		return presentation;
	}
	
	@Override
	public SseEmitter newSseEmitter() {
		SseEmitter emitter = new SseEmitter();
		
		emitters.add(emitter);
		System.out.println(emitter);
		emitter.onCompletion(() -> {
			emitters.remove(emitter);
			System.err.println(emitter);
		});
		
		return emitter;
	}
	
	private void disableCurrentlyActives() {
		/* should not have more than one, but just in case */
		List<Presentation> actives = repository.findAllByActiveTrue();
		
		actives.forEach((presentation) -> presentation.setActive(false));
		
		repository.saveAll(actives);
	}
	
	private void notifyActive(Presentation presentation) {
		log.info("Active presentation updated");
		
		notifyPresentationUpdate(presentation.getId());
	}
	
	private void notifyNoActive() {
		log.info("No active presentation");
		
		notifyPresentationUpdate(-1);
	}
	
	private void notifyPresentationUpdate(long id) {
		sendToEmitters(SseEmitter.event()
				.name(EVENT_NOTIFY_UPDATE)
				.data(id));
	}
	
	private void sendToEmitters(SseEmitter.SseEventBuilder event) {
		List<SseEmitter> failedEmitters = new ArrayList<>();
		
		emitters.forEach((emitter) -> {
			try {
				emitter.send(event);
			} catch (IOException exception) {
				log.warn("Could not send message", exception);
				failedEmitters.add(emitter);
			}
		});
		
		emitters.removeAll(failedEmitters);
	}
	
	@Override
	public void destroy() throws Exception {
		emitters.forEach(SseEmitter::complete);
	}
	
	@Override
	public String getEntityName() {
		return ENTITY;
	}
	
}