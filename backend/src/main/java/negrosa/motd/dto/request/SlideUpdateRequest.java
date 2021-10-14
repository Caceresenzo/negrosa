package negrosa.motd.dto.request;

import javax.validation.constraints.Positive;

import lombok.Data;

@Data
public class SlideUpdateRequest {
	
	@Positive
	private Double duration;
	
}