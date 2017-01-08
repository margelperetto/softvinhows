package br.com.margel.softvinhows;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.ws.rs.core.GenericType;

import br.com.margel.softvinhows.models.Cliente;

public class TestCliente extends AbstractBasicBeanTest<Cliente>{
	
	protected Cliente createTestInstanceWithoutId(long unique){
		return new Cliente("Cliente teste "+unique);
	}
	
	protected void executeAllAssertsExceptId(Cliente obj, Cliente other){
		assertEquals(obj.getNome(), other.getNome());
	}

	@Override
	protected Class<Cliente> clazz() {
		return Cliente.class;
	}

	@Override
	protected String path() {
		return "cliente";
	}

	@Override
	protected GenericType<List<Cliente>> genericTypeListInstance() {
		return new GenericType<List<Cliente>>(){};
	}

}