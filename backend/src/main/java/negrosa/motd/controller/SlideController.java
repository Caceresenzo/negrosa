package negrosa.motd.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import negrosa.motd.dto.request.SlideUpdateRequest;
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
	
	@PatchMapping("{id}")
	@JsonView(Slide.Fields.Presentation.class)
	public Slide update(@PathVariable long id, @RequestBody @Validated SlideUpdateRequest body) {
		Slide slide = slideService.byId(id);
		
		return slideService.update(slide, body);
	}
	
	@GetMapping("{id}/image")
	public ResponseEntity<?> showImage(@PathVariable long id) throws IOException {
		Slide slide = slideService.byId(id);
		
		try (InputStream inputStream = new FileInputStream(new File(slide.getFile()))) {
			return ResponseEntity
					.ok()
					.contentType(MediaType.IMAGE_PNG)
					.body(StreamUtils.copyToByteArray(inputStream));
		}
	}
	
}