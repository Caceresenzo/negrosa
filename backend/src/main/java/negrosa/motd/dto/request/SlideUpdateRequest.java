package negrosa.motd.dto.request;

import javax.validation.constraints.PositiveOrZero;

import lombok.Data;

@Data
public class SlideUpdateRequest {
	
	@PositiveOrZero
	private Double duration;
	
}