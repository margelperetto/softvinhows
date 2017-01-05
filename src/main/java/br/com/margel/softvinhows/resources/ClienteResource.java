package br.com.margel.softvinhows.resources;

import javax.ws.rs.Path;

import br.com.margel.softvinhows.models.Cliente;

@Path("/cliente")
public class ClienteResource extends BasicBeanResource<Cliente> {

	public ClienteResource() {
		super(Cliente.class);
	}

}
