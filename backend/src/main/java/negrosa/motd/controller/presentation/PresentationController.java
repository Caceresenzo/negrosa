package negrosa.motd.controller.presentation;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;

import negrosa.motd.dto.request.PresentationUpdateRequest;
import negrosa.motd.entity.Presentation;
import negrosa.motd.entity.Slide;

@Validated
@RestController
@RequestMapping(path = "motd/presentations", produces = MediaType.APPLICATION_JSON_VALUE)
public class PresentationController extends BasePresentationController {
	
	@GetMapping
	@JsonView(Void.class)
	@Override
	public List<Presentation> index() {
		return super.index();
	}
	
	@PostMapping
	@JsonView(Presentation.Fields.Slides.class)
	@Override
	public Presentation create(@RequestParam MultipartFile file) throws IOException {
		return super.create(file);
	}
	
	@GetMapping("{id}")
	@JsonView(Void.class)
	public Presentation show(@PathVariable long id) {
		return with(id, super::show);
	}
	
	@PatchMapping("{id}")
	@JsonView(Void.class)
	public Presentation update(@PathVariable long id, @RequestBody @Validated PresentationUpdateRequest body) {
		return with(id, (presentation) -> super.update(presentation, body));
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable long id) {
		withDo(id, super::delete);
	}
	
	@GetMapping("{id}/slides")
	@JsonView(Void.class)
	public List<Slide> showSlides(@PathVariable long id) {
		return with(id, super::showSlides);
	}
	
	private <T> T with(long id, Function<Presentation, T> function) {
		Presentation presentation = presentationService.byId(id);
		
		return function.apply(presentation);
	}
	
	private void withDo(long id, Consumer<Presentation> consumer) {
		Presentation presentation = presentationService.byId(id);
		
		consumer.accept(presentation);
	}
	
}