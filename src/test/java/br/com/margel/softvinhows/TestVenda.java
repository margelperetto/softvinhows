package br.com.margel.softvinhows;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;

import org.junit.Test;

import br.com.margel.softvinhows.models.Cliente;
import br.com.margel.softvinhows.models.ItemVenda;
import br.com.margel.softvinhows.models.Venda;
import br.com.margel.softvinhows.models.Vinho;
import br.com.margel.softvinhows.models.Vinho.Tipo;

public class TestVenda extends GrizzlyTest{

	@Test
	public void testRegistrarGetDeleteVenda(){
		System.out.println("TESTANDO REGISTRO DE VENDA");
		
		Venda retSave = saveVendaTeste();
		
		assertVendaTesteTotaisVendaTotaisItens(retSave,1);
		
		Venda retGet = target.path("venda/get/1")
				.request()
				.get(Venda.class);
		
		assertVendaTesteTotaisVendaTotaisItens(retGet,1);
		
		Boolean retDelete = target.path("venda/delete/1")
				.request()
				.delete(Boolean.class);
		
		assertEquals(Boolean.TRUE, retDelete);
		
		System.out.println("REGISTRO DE VENDA OK!");
	}
	
	@Test
	public void testCalculoFrete(){
		System.out.println("TESTANDO CALCULO DE FRETE");
		
		/*calcular_frete/{distancia}/{peso} usar parampath eh menos intuitivo, mas eh mais simples :) */
		
		BigDecimal freteSimples = target.path("venda/calcular_frete/100/4.125")
				.request()
				.get(BigDecimal.class);
		
		assertEquals(new BigDecimal("20.62"),  freteSimples);//4,125 kg * R$5,00 = 20,625 trunk: R$20,62
		
		BigDecimal freteDistanciaMaiorCem = target.path("venda/calcular_frete/110/4.125")
				.request()
				.get(BigDecimal.class);
		
		assertEquals(new BigDecimal("22.68"),  freteDistanciaMaiorCem);//R$20,62 x 110 km / 100 = 22,682 (trunk: R$22,68)
		
		System.out.println("CALCULO DE FRETE OK!");
	}
	
	@Test
	public void testListVendas(){
		System.out.println("TESTANDO LIST DE VENDA");
		
		saveVendaTeste();
		saveVendaTeste();
		
		List<Venda> vendas = target.path("venda")
				.request()
				.get(new GenericType<List<Venda>>(){});
		
		assertEquals(2, vendas.size());
		
		Venda v1 = vendas.get(0);
		Venda v2 = vendas.get(1);
		
		assertVendaTesteTotaisVendaTotaisItens(v1,1);
		assertVendaTesteTotaisVendaTotaisItens(v2,2);
		
		assertEquals(1, v1.getCliente().getId());
		assertEquals(2, v2.getCliente().getId());
		
		assertEquals(2, v1.getItens().size());
		assertEquals(2, v2.getItens().size());
		
		assertEquals(1, v1.getItens().get(0).getId());
		assertEquals(2, v1.getItens().get(1).getId());
		
		assertEquals(3, v2.getItens().get(0).getId());
		assertEquals(4, v2.getItens().get(1).getId());
		
		System.out.println("LIST DE VENDA OK!");
	}
	
	private void assertVendaTesteTotaisVendaTotaisItens(Venda v, long id){
		assertEquals(id, v.getId());
		
		assertEquals(2, v.getItens().size());
		
		ItemVenda i1 = v.getItens().get(0);
		ItemVenda i2 = v.getItens().get(1);
		
		assertEquals(new BigDecimal("49.76"), i1.getTotalItem());
		assertEquals(new BigDecimal("50.25"), i2.getTotalItem());//5×R$10,05 = R$50,25
		assertEquals(new BigDecimal("0.375"), i1.getPesoTotalItem());
		assertEquals(new BigDecimal("3.750"),  i2.getPesoTotalItem());//5×0,750 = 3,750kg
		
		assertEquals(new BigDecimal("100.01"), v.getTotalItens());//R$49,76+(5×R$10,05) = R$100,01
		assertEquals(new BigDecimal("4.125"),  v.getPesoTotal());//0,375+(5×0,750) = 4,125 kg
		assertEquals(new BigDecimal("20.62"),  v.getTotalFrete());//4,125 kg * R$5,00 = 20,625 trunk: R$20,62
		assertEquals(new BigDecimal("120.63"), v.getTotalGeral());//itens: R$100,01 + R$20,62 frete
	}
	
	private Venda saveVendaTeste(){
		Cliente cliente = saveClienteTeste();

		Vinho v1 = saveVinhoTeste();
		Vinho v2 = saveVinhoTeste();

		List<ItemVenda> itens = new LinkedList<>();
		itens.add(new ItemVenda(
				v1,//vinho, 
				BigDecimal.ONE,//quantidade, 
				new BigDecimal("49.76"),//valorUnitario, 
				new BigDecimal("0.375")//pesoVinho
				));

		itens.add(new ItemVenda(
				v2,//vinho, 
				new BigDecimal("5"),//quantidade, 
				new BigDecimal("10.05"),//valorUnitario, 
				new BigDecimal("0.750")//pesoVinho
				));

		Venda venda = new Venda(
				cliente,//cliente, 
				BigDecimal.TEN,//distancia, 
				itens//itens
				);

		return target.path("venda/save")
				.request()
				.put(Entity.json(venda),Venda.class);
	}

	private Cliente saveClienteTeste(){
		return target.path("cliente/save")
				.request()
				.put(Entity.json(new Cliente("Cliente teste")),Cliente.class);
	}

	private Vinho saveVinhoTeste(){
		
		Vinho vinho = new Vinho(
				"Vinho teste", //nome
				new BigDecimal("0.375"),//peso 
				new BigDecimal("9.99"), //valor sugerido
				Tipo.TINTO //tipo
				);
		
		return target.path("vinho/save")
				.request()
				.put(Entity.json(vinho),Vinho.class);
	}

}