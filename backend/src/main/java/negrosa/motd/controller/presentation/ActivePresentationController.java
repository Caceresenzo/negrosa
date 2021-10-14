package negrosa.motd.controller.presentation;

import java.util.List;
import java.util.function.Function;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import negrosa.motd.dto.request.PresentationUpdateRequest;
import negrosa.motd.entity.Presentation;
import negrosa.motd.entity.Slide;

@Validated
@RestController
@RequestMapping(path = "motd/presentations/@active", produces = MediaType.APPLICATION_JSON_VALUE)
public class ActivePresentationController extends BasePresentationController {
	
	@GetMapping
	@JsonView(Void.class)
	public Presentation show() {
		return with(super::show);
	}
	
	@PatchMapping
	@JsonView(Void.class)
	public Presentation update(@PathVariable long id, @RequestBody @Validated PresentationUpdateRequest body) {
		return with((presentation) -> super.update(presentation, body));
	}
	
	@GetMapping("slides")
	@JsonView(Void.class)
	public List<Slide> showSlides() {
		return with(super::showSlides);
	}
	
	private <T> T with(Function<Presentation, T> function) {
		Presentation presentation = presentationService.byActive();
		
		return function.apply(presentation);
	}
	
}