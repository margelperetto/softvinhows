package br.com.margel.softvinhows.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("recurso_teste")
public class RecursoTeste {
 
	public static final String TESTE = "Isto é um teste, tudo esta funcionando!";
	
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String teste1() {
    	System.out.println("RECURSO TESTE ACESSADO!");
        return TESTE;
    }
    
    @GET
    @Path("teste2")
    @Produces(MediaType.TEXT_PLAIN)
    public String teste2() {
    	System.out.println("RECURSO TESTE 2 ACESSADO!");
    	return "Esta é uma outra mensagem com path 'teste2'";
    }
    
    @GET
    @Path("teste3")
    @Produces(MediaType.TEXT_PLAIN)
    public String teste3() {
    	System.out.println("RECURSO TESTE 3 ACESSADO!");
    	return "Aqui testando para o path 'teste3'";
    }
    
    @GET
    @Path("testeparam/{param}")
    @Produces(MediaType.TEXT_PLAIN)
    public String testeParam(@PathParam("param")String param) {
    	System.out.println("TESTE COM PARAMETRO: "+param);
    	return "Teste param: "+param;
    }
    
    @SuppressWarnings("null")
	@GET
    @Path("teste_erro")
    @Produces(MediaType.TEXT_PLAIN)
    public String testeErro() {
    	String teste = null;
    	teste.toString();
    	return "isso nunca será visto :(";
    } 
    
}
