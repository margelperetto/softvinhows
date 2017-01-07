package br.com.margel.softvinhows.resources;

import javax.ws.rs.Path;
import br.com.margel.softvinhows.models.Vinho;

@Path("/vinho")
public class VinhoResource extends BasicBeanResource<Vinho> {

	public VinhoResource() {
		super(Vinho.class);
	}
	
}