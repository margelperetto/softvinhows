package br.com.margel.softvinhows;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Db {
	
	public static String POSTGRESQL_UNIT = "postgresql";
	public static String H2_UNIT = "h2_memoria";
	
	public static String PERSISTENCE_UNIT = POSTGRESQL_UNIT;
	
	private static EntityManagerFactory factory;
	private static ThreadLocal<EntityManager> tr = new ThreadLocal<>();
	
	public static void createFactory(){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		System.out.println("Factory criado para a unidade  de persistência: "+PERSISTENCE_UNIT);
	}
	
	public static void closeFactory(){
		if(factory != null){
			rollback();
			closeEm();
			factory.close();
			System.out.println("Factory fechado!");
		}
	}

	/**
	 * Retorna a sessão do EntityManager para esta conexão
	 * @return EntityManager
	 */
	public static synchronized EntityManager em(){
		if(tr.get()==null){
			tr.set(factory.createEntityManager());
			System.out.println("EntityManager criado!");
			begin();
		}
		return tr.get();
	}

	private static void begin() {
		if(tr.get().getTransaction()!=null && !tr.get().getTransaction().isActive()){
			tr.get().getTransaction().begin();;
			System.out.println("Transação iniciada!");
		}else{
			System.out.println("Nenhuma transação para iniciar!");
		}
	}
	
	public static void commit() {
		if(tr.get()!=null && tr.get().getTransaction()!=null && tr.get().getTransaction().isActive()){
			tr.get().getTransaction().commit();
			System.out.println("Commit na Transação!");
		}else{
			System.out.println("Nenhuma transação para commit!");
		}
	}
	
	public static void rollback() {
		if(tr.get()!=null && tr.get().getTransaction()!=null && tr.get().getTransaction().isActive()){
			tr.get().getTransaction().rollback();
			System.out.println("Rollback na Transação!");
		}else{
			System.out.println("Nenhuma transação para rollback!");
		}
	}
	
	public static void closeEm() {
		if(tr.get()!=null){
			if(tr.get().isOpen()){
				tr.get().close();
				System.out.println("EntityManager fechado!");
			}
			tr.remove();
			System.out.println("Thread Local removida!");
		}else{
			System.out.println("Nada incluído na Thread Local");
		}
	}

}