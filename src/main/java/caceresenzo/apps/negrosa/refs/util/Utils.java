package caceresenzo.apps.negrosa.refs.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.SneakyThrows;

public class Utils {
	
	public static final ObjectMapper OBJECT_MAPPER;
	
	static {
		OBJECT_MAPPER = new ObjectMapper();
		OBJECT_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
	}
	
	@SneakyThrows
	public static String dataToJson(Object data) {
		return OBJECT_MAPPER.writeValueAsString(data);
	}
	
}