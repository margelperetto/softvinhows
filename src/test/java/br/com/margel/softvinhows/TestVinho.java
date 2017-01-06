package br.com.margel.softvinhows;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import javax.ws.rs.core.GenericType;

import br.com.margel.softvinhows.models.Vinho;
import br.com.margel.softvinhows.models.Vinho.Tipo;

public class TestVinho extends AbstractBasicBeanTest<Vinho>{

	@Override
	protected Vinho createTestInstanceWithoutId(int seq) {
		return new Vinho(
				"Vinho teste "+seq, 
				new BigDecimal("0.575"), 
				new BigDecimal("45.99"), 
				Tipo.TINTO
				);
	}

	@Override
	protected void executeAllAssertsExceptId(Vinho obj, Vinho other) {
		assertEquals(obj.getNome(), other.getNome());
		assertEquals(obj.getTipo(), other.getTipo());
//		assertEquals(obj.getPeso(), other.getPeso());
//		assertEquals(obj.getPrecoSugerido(), other.getPrecoSugerido());
	}

	@Override
	protected Class<Vinho> clazz() {
		return Vinho.class;
	}

	@Override
	protected String path() {
		return "vinho";
	}

	@Override
	protected GenericType<List<Vinho>> genericTypeListInstance() {
		return new GenericType<List<Vinho>>(){};
	}
	
}