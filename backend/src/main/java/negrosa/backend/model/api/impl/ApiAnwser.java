package negrosa.backend.model.api.impl;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
public class ApiAnwser<T> extends AbstractApiResponse {

	/* Variables */
	@JsonInclude(Include.NON_NULL)
	@JsonView(View.Public.class)
	private T payload;
	
	/* Constructor */
	public ApiAnwser() {
		this((T) null);
	}
	
	/* Constructor */
	public ApiAnwser(T payload) {
		this(HttpStatus.OK, payload);
	}

	/* Constructor */
	public ApiAnwser(HttpStatus status) {
		this(status, null);
	}

	/* Constructor */
	public ApiAnwser(HttpStatus status, T payload) {
		super(status);
		
		this.payload = payload;
	}
	
}