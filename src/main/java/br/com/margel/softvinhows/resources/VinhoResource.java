package br.com.margel.softvinhows.resources;

import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import br.com.margel.softvinhows.models.Vinho;

@Path("/vinho")
public class VinhoResource extends BasicBeanResource<Vinho> {

	public VinhoResource() {
		super(Vinho.class);
	}
	
	@Override
	protected void validarSave(Vinho obj) throws WebApplicationException {
		if(obj.getNome()==null || obj.getNome().isEmpty()){
			throw builBadRequest("Nome não informado para o vinho! "+obj);
		}
	}
	
}