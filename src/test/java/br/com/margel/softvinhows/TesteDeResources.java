package br.com.margel.softvinhows;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.margel.softvinhows.resources.RecursoTeste;

import static org.junit.Assert.assertEquals;

import java.net.URI;

public class TesteDeResources {

    private HttpServer server;
    private WebTarget target;
    public static final String BASE_URI = "http://localhost:8088/softvinho_test/";
    
    private HttpServer startGrizzlyServer() {
        ResourceConfig rc = new ResourceConfig().packages("br.com.margel.softvinhows.resources");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    @Before
    public void setUp() throws Exception {
        
        server = startGrizzlyServer();
        Client c = ClientBuilder.newClient();

        target = c.target(BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdownNow();
    }

    @Test
    public void testRecursoTeste() {
    	System.out.println("Testando RECURSO TESTE! ------------");
    	
        String resposta = target.path("recurso_teste").request().get(String.class);
        assertEquals(RecursoTeste.TESTE, resposta);
        
        System.out.println("RECURSO TESTE ----------- OK");
    }
}
