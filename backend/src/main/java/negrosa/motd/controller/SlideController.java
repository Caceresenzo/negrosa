package negrosa.motd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import negrosa.motd.entity.Slide;
import negrosa.motd.service.SlideService;

@Validated
@RestController
@RequestMapping(path = "motd/slides", produces = MediaType.APPLICATION_JSON_VALUE)
public class SlideController {
	
	@Autowired
	private SlideService slideService;
	
	@GetMapping("{id}")
	@JsonView(Slide.Fields.Presentation.class)
	public Slide show(@PathVariable long id) {
		return slideService.byId(id);
	}
	
}