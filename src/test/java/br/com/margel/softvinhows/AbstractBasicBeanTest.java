package br.com.margel.softvinhows;

import static org.junit.Assert.assertEquals;
import java.util.List;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import org.junit.Test;
import br.com.margel.softvinhows.models.BasicBean;

public abstract class AbstractBasicBeanTest<T extends BasicBean> extends GrizzlyTest{
	
	protected abstract T createTestInstanceWithoutId(int seq);
	
	protected abstract void executeAllAssertsExceptId(T obj, T other);
	
	protected abstract Class<T> clazz();
	
	protected abstract GenericType<List<T>> genericTypeListInstance();
	
	protected abstract String path();
	
	@Test
	public void testAdd() {
		System.out.println("TESTANDO ADD DE "+path());

		T testInstance = createTestInstanceWithoutId(1);
		
		T resposta = save(testInstance);

		assertEquals(resposta.getId(), 1);
		executeAllAssertsExceptId(testInstance, resposta);

		System.out.println("ADD DE "+path()+" OK!");
	}

	@Test
	public void testUpdate() {
		System.out.println("TESTANDO UPDATE DE "+path());

		T testInstance = createTestInstanceWithoutId(1);
		
		T respSave = save(testInstance);

		assertEquals(respSave.getId(), 1);
		executeAllAssertsExceptId(testInstance, respSave);

		T objAlterado = createTestInstanceWithoutId(2);
		objAlterado.setId(respSave.getId());

		T respUpdate = save(objAlterado);

		assertEquals(respUpdate.getId(), 1);
		executeAllAssertsExceptId(respUpdate, objAlterado);

		T retGet = doGetId1();

		assertEquals(retGet.getId(), 1);
		executeAllAssertsExceptId(retGet, respUpdate);

		System.out.println("UPDATE DE "+path()+" OK!");
	}

	@Test
	public void testDelete() {
		System.out.println("TESTANDO DELETE DE "+path());

		T testInstance = createTestInstanceWithoutId(1);
		
		T respSave = save(testInstance);

		assertEquals(respSave.getId(), 1);

		Boolean b = target.path(path()+"/delete/1")
				.request()
				.delete(Boolean.class);

		assertEquals(b, Boolean.TRUE);
		System.out.println("DELETE DE "+path()+" OK!");
	}

	@Test
	public void testGet() {
		System.out.println("TESTANDO GET DE "+path());
		
		T testInstance = createTestInstanceWithoutId(1);

		T respSave = save(testInstance);

		assertEquals(respSave.getId(), 1);

		T retGet = doGetId1();

		assertEquals(retGet.getId(), 1);
		executeAllAssertsExceptId(respSave, retGet);

		System.out.println("GET DE "+path()+" OK!");
	}

	@Test
	public void testList() {
		System.out.println("TESTANDO LIST DE "+path());

		T saved1 = save(createTestInstanceWithoutId(1));
		T saved2 = save(createTestInstanceWithoutId(2));

		List<T> retList = target.path(path())
				.request()
				.get(genericTypeListInstance());

		assertEquals(retList.size(), 2);
		assertEquals(retList.get(0).getId(), 1);
		assertEquals(retList.get(1).getId(), 2);
		
		executeAllAssertsExceptId(saved1, retList.get(0));
		executeAllAssertsExceptId(saved2, retList.get(1));

		System.out.println("LIST DE "+path()+" OK!");
	}

	protected T save(T obj){
		return target.path(path()+"/save")
		.request()
		.put(Entity.json(obj),clazz());
	}

	protected T doGetId1() {
		return target.path(path()+"/get/1")
				.request()
				.get(clazz());
	}
}