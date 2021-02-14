package negrosa.references.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import negrosa.references.service.ReferenceService;
import negrosa.references.util.Respond;

@Validated
@RestController
@RequestMapping(path = "references", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReferenceApiController {
	
	@Autowired
	private ReferenceService referenceService;
	
	@GetMapping
	public ResponseEntity<?> all() {
		return Respond.auto(referenceService::all);
	}
	
	@PostMapping
	public ResponseEntity<?> store(@RequestParam("file") MultipartFile file) {
		return Respond.auto(() -> referenceService.store(file));
	}
	
	@GetMapping("{reference}")
	public ResponseEntity<?> find(@PathVariable String reference) {
		try {
			return ResponseEntity
					.ok()
					.contentType(MediaType.IMAGE_JPEG)
					.body(StreamUtils.copyToByteArray(referenceService.find(reference)));
		} catch (Exception exception) {
			return Respond.auto(() -> {
				throw exception;
			});
		}
	}
	
	@DeleteMapping("{reference}")
	public ResponseEntity<?> delete(@PathVariable String reference) {
		return Respond.auto(() -> {
			referenceService.delete(reference);
			
			return null;
		});
	}
	
}