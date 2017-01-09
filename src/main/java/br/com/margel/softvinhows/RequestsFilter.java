package br.com.margel.softvinhows;

import java.io.IOException;
import javax.persistence.PersistenceException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.hibernate.exception.ConstraintViolationException;

@Provider
public class RequestsFilter implements ContainerRequestFilter,ContainerResponseFilter, ExceptionMapper<Throwable>{

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		System.out.println("REQUISIÇÃO INICIADA!");
	}

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
		Db.commit();
		Db.closeEm();
		System.out.println("FINALIZANDO REQUISIÇÃO...\n");
		
		responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
		responseContext.getHeaders().add("Access-Control-Allow-Methods", "PUT, GET, POST, DELETE, OPTIONS, PATCH");
		responseContext.getHeaders().add("Access-Control-Allow-Headers", "Content-Type, Accept, token, api_key, Authorization, Origin, X-Requested-With");
		responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
	}

	@Override
	public Response toResponse(Throwable exception) {
		System.out.println("UM ERRO OCORREU NA REQUISIÇÃO! "+(exception==null?"NULL":exception.getClass().getSimpleName()));
		Response resp;
		if(exception instanceof WebApplicationException){
			resp = ((WebApplicationException)exception).getResponse();
		}else if(exception instanceof PersistenceException){
			System.out.println("ERRO DE PERSISTÊNCIA!");
			ConstraintViolationException cve = findIntegrityException(exception);
			if(cve!=null){
				resp = Response.status(Response.Status.CONFLICT)
						.build();
			}else{
				System.out.println("ConstraintViolationException NÃO ENCONTRADA!");
				exception.printStackTrace();
				resp = Response.status(422).build();
			}
		}else{ 
			System.out.println("ERRO INESPERADO!");
			if(exception!=null){
				exception.printStackTrace();
			}
			resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.build();
		}
		Db.rollback();
		return resp;
	}
	
	public ConstraintViolationException findIntegrityException(Throwable e){
		if(e == null){
			return null;
		}
		if(e instanceof ConstraintViolationException){
			return (ConstraintViolationException)e;
		}
		return findIntegrityException(e.getCause());
	}

}