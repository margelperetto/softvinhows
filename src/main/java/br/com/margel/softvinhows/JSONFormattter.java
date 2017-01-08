package br.com.margel.softvinhows;

import java.text.SimpleDateFormat;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("serial")
@Provider
public class JSONFormattter extends ObjectMapper implements ContextResolver<ObjectMapper>{

	private final ObjectMapper mapper;
	
	private SimpleDateFormat formatter = 
		      new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	
	
	public JSONFormattter() {
		mapper = new ObjectMapper();
		mapper.setDateFormat(formatter);
	}
	
	@Override
	public ObjectMapper getContext(Class<?> type) {
		return mapper;
	}

	
}
