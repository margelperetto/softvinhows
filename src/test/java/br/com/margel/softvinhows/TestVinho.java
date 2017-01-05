package br.com.margel.softvinhows;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;

import org.junit.Test;

import br.com.margel.softvinhows.models.Cliente;

public class TestVinho extends GrizzlyTest{

	@Test
	public void testAddCliente() {
		System.out.println("TESTANDO ADD DE CLIENTE");

		Cliente resposta = target.path("cliente/save")
				.request()
				.put(Entity.json(new Cliente("Cliente teste")),Cliente.class);

		assertEquals(resposta.getId(), 1);
		
		System.out.println("ADD DE CLIENTE OK!");
	}
	
	@Test
	public void testUpdateCliente() {
		System.out.println("TESTANDO UPDATE DE CLIENTE");

		Cliente respSave = target.path("cliente/save")
				.request()
				.put(Entity.json(new Cliente("Cliente teste")),Cliente.class);

		assertEquals(respSave.getId(), 1);
		assertEquals(respSave.getNome(), "Cliente teste");
		
		respSave.setNome("Cliente alterado");
		
		Cliente respUpdate = target.path("cliente/save")
				.request()
				.put(Entity.json(respSave),Cliente.class);
		
		assertEquals(respUpdate.getId(), 1);
		assertEquals(respUpdate.getNome(), "Cliente alterado");
		
		Cliente retGet = target.path("cliente/get/1")
				.request()
				.get(Cliente.class);
		
		assertEquals(retGet.getId(), 1);
		assertEquals(retGet.getNome(), "Cliente alterado");
		
		System.out.println("UPADTE DE CLIENTE OK!");
	}
	
	@Test
	public void testDeleteCliente() {
		System.out.println("TESTANDO DELETE DE CLIENTE");
		
		Cliente resSave = target.path("cliente/save")
				.request()
				.put(Entity.json(new Cliente("Cliente teste")),Cliente.class);

		assertEquals(resSave.getId(), 1);
		
		Boolean b = target.path("cliente/delete/1")
				.request()
				.delete(Boolean.class);
		
		assertEquals(b, Boolean.TRUE);
		System.out.println("DELETE DE CLIENTE OK!");
	}
	
	@Test
	public void testGetCliente() {
		System.out.println("TESTANDO GET DE CLIENTE");
		
		Cliente resposta = target.path("cliente/save")
				.request()
				.put(Entity.json(new Cliente("Cliente teste")),Cliente.class);
		
		assertEquals(resposta.getId(), 1);
		
		Cliente retGet = target.path("cliente/get/1")
				.request()
				.get(Cliente.class);
		
		assertEquals(retGet.getId(), 1);
		assertEquals(retGet.getNome(), "Cliente teste");
		
		System.out.println("GET DE CLIENTE OK!");
	}
	
	@Test
	public void testListCliente() {
		System.out.println("TESTANDO LIST DE CLIENTE");
		
		target.path("cliente/save")
				.request()
				.put(Entity.json(new Cliente("Cliente teste 1")));
		
		target.path("cliente/save")
				.request()
				.put(Entity.json(new Cliente("Cliente teste 2")));
		
		List<Cliente> retList = target.path("cliente")
				.request()
				.get(new GenericType<List<Cliente>>(){});
		
		assertEquals(retList.size(), 2);
		assertEquals(retList.get(0).getId(), 1);
		assertEquals(retList.get(1).getId(), 2);
		assertEquals(retList.get(0).getNome(), "Cliente teste 1");
		assertEquals(retList.get(1).getNome(), "Cliente teste 2");
		
		System.out.println("LIST DE CLIENTE OK!");
	}
	
}