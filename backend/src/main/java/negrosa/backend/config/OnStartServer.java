package negrosa.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class OnStartServer implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	@Qualifier("requestMappingHandlerAdapter")
	private RequestMappingHandlerAdapter adapter;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		for (HttpMessageConverter<?> convertor : adapter.getMessageConverters()) {
			if (convertor instanceof MappingJackson2HttpMessageConverter) {
				((MappingJackson2HttpMessageConverter) convertor).setObjectMapper(objectMapper);
				break;
			}
		}
	}
	
}