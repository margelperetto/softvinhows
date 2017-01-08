package br.com.margel.softvinhows.resources;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.margel.softvinhows.Db;
import br.com.margel.softvinhows.models.BasicBean;

public abstract class BasicBeanResource<T extends BasicBean> {
	
	private Class<T> clazz;
	
	/* Uma lib de log seria bem melhor que sysout, mas to cheio de visita querendo atenção nesse fim de ano 
	 * e to meio sem tempo pra implementar :) */
	
	public BasicBeanResource(Class<T> clazz){
		this.clazz = clazz;
		System.out.println("ACESSO AO RECURSO PARA "+clazz.getSimpleName());
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("save")
	public T save( T obj) {
		System.out.println("SALVANDO "+obj);
		validarSave(obj);
		if(obj.getId()>0){
			obj = Db.em().merge(obj);
		}else{
			Db.em().persist(obj);
		}
		return obj;
	}
	
	protected abstract void validarSave(T obj) throws WebApplicationException;

	@DELETE
	@Path("delete/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public boolean delete(@PathParam("id") long id) {
		System.out.println("REMOVENDO ID: "+id);
		T T = Db.em().find(clazz, id);
		if(T==null){
			return false;
		}
		Db.em().remove(T);
		return true;
	}
	
	@GET
	@Path("get/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public T get(@PathParam("id") long id) {
		System.out.println("BUSCANDO ID: "+id);
		T obj = Db.em().find(clazz, id);
		return obj;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<T> listAll() {
		System.out.println("LISTANDO TODOS");
		CriteriaBuilder builder = Db.em().getCriteriaBuilder();

		CriteriaQuery<T> query = builder.createQuery(clazz);
		Root<T> r = query.from(clazz);
		query.select(r);
		TypedQuery<T> typed = Db.em().createQuery(query);

		List<T> list = typed.getResultList();
		return list;
	}
	
	protected static WebApplicationException builBadRequest(String msg){
		return new WebApplicationException(Response.status(Status.BAD_REQUEST).entity(msg).build());
	}

}
