package caceresenzo.apps.negrosa.refs.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@RequiredArgsConstructor
public class ConversionResult {
	
	private boolean success;
	private String file;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String error;
	
}