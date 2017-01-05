package br.com.margel.softvinhows;

import static org.junit.Assert.assertEquals;
import java.math.BigDecimal;
import javax.ws.rs.client.Entity;
import org.junit.Test;
import br.com.margel.softvinhows.models.Vinho;
import br.com.margel.softvinhows.models.Vinho.Tipo;

public class TestVinho extends GrizzlyTest{

	@Test
	public void testAddVinho() {
		System.out.println("TESTANDO ADD DE VINHO");

		Vinho vinho = new Vinho(
				"Vinho teste", 
				new BigDecimal("0.575"), 
				new BigDecimal("45.99"), 
				Tipo.TINTO
				);

		Vinho resposta = target.path("vinho/save")
				.request()
				.put(Entity.json(vinho),Vinho.class);

		assertEquals(resposta.getId(), 1);

		System.out.println("ADD DE VINHO OK!");
	}

}