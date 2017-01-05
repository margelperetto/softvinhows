package br.com.margel.softvinhows;

import java.io.IOException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

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
	}

	@Override
	public Response toResponse(Throwable exception) {
		System.out.println("UM ERRO OCORREU NA REQUISIÇÃO! "+(exception==null?"NULL":exception.getClass().getSimpleName()));
		Response resp;
		if(exception instanceof WebApplicationException){
			resp = ((WebApplicationException)exception).getResponse();
		}else{ 
			if(exception!=null){
				exception.printStackTrace();
			}
			resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.build();
		}
		Db.rollback();
		return resp;
	}

}