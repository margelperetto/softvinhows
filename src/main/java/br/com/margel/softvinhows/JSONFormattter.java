package br.com.margel.softvinhows;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@SuppressWarnings("serial")
@Provider
public class JSONFormattter extends ObjectMapper implements ContextResolver<ObjectMapper>{

	private final ObjectMapper mapper;
	
	public JSONFormattter() {
		mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	}
	
	@Override
	public ObjectMapper getContext(Class<?> type) {
		return mapper;
	}

	
}
