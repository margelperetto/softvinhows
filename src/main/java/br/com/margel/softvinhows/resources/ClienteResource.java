package br.com.margel.softvinhows.resources;

import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;

import br.com.margel.softvinhows.models.Cliente;

@Path("/cliente")
public class ClienteResource extends BasicBeanResource<Cliente> {

	public ClienteResource() {
		super(Cliente.class);
	}

	@Override
	protected void validarSave(Cliente obj) throws WebApplicationException {
		if(obj.getNome()==null || obj.getNome().isEmpty()){
			throw builBadRequest("Nome não informado para o cliente! "+obj);
		}
	}

}
