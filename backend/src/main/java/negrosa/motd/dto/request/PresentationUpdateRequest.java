package negrosa.motd.dto.request;

import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class PresentationUpdateRequest {
	
	@Length(min = 1, max = 200)
	private String name;
	
	private Boolean active;
	
	@Positive
	private Double slideDuration;
	
}