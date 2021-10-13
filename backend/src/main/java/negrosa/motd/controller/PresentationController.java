	package negrosa.motd.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.fasterxml.jackson.annotation.JsonView;

import negrosa.motd.dto.request.PresentationUpdateRequest;
import negrosa.motd.entity.Presentation;
import negrosa.motd.entity.Slide;
import negrosa.motd.service.PresentationService;

@Validated
@RestController
@RequestMapping(path = "motd/presentations", produces = MediaType.APPLICATION_JSON_VALUE)
public class PresentationController {
	
	@Autowired
	private PresentationService presentationService;
	
	@GetMapping
	@JsonView(Void.class)
	public List<Presentation> index() {
		return presentationService.findAll();
	}
	
	@PostMapping
	@JsonView(Presentation.Fields.Slides.class)
	public Presentation create(@RequestParam MultipartFile file) throws IOException {
		return presentationService.create(file);
	}
	
	@GetMapping("{id}")
	@JsonView(Void.class)
	public Presentation show(@PathVariable long id) {
		return presentationService.byId(id);
	}
	
	@PatchMapping("{id}")
	@JsonView(Void.class)
	public Presentation update(@PathVariable long id, @RequestBody @Validated PresentationUpdateRequest body) {
		Presentation presentation = presentationService.byId(id);
		
		return presentationService.update(presentation, body);
	}
	
	@GetMapping("{id}/slides")
	@JsonView(Void.class)
	public List<Slide> showSlides(@PathVariable long id) {
		Presentation presentation = presentationService.byId(id);
		
		return presentation.getSlides();
	}
	
	@GetMapping("active")
	@JsonView(Void.class)
	public Presentation showActive() throws IOException {
		return presentationService.byActive();
	}
	
	@GetMapping(path = "events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@CrossOrigin
	@JsonView(Void.class)
	public SseEmitter events() throws IOException {
		return presentationService.newSseEmitter();
	}
	
}