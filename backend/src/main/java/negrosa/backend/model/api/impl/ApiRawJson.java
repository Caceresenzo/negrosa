package negrosa.backend.model.api.impl;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import negrosa.backend.model.api.AbstractApiResponse;
import negrosa.common.view.View;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonPropertyOrder({ "status", "payload", "metadata" })
public class ApiRawJson extends AbstractApiResponse {
	
	/* Variables */
	@JsonInclude(Include.NON_NULL)
	@JsonRawValue
	@JsonView(View.Public.class)
	private String payload;
	
	/* Constructor */
	public ApiRawJson() {
		this((String) null);
	}
	
	/* Constructor */
	public ApiRawJson(String payload) {
		this(HttpStatus.OK, payload);
	}
	
	/* Constructor */
	public ApiRawJson(HttpStatus status) {
		this(status, null);
	}
	
	/* Constructor */
	public ApiRawJson(HttpStatus status, String payload) {
		super(status);
		
		this.payload = payload;
	}
	
}