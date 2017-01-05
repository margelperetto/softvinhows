package br.com.margel.softvinhows;

import javax.servlet.Servlet;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.uri.UriComponent;
import org.junit.After;
import org.junit.Before;
import java.net.URI;

public class GrizzlyTest {

	private Client c;
	protected WebTarget target;
	
	private HttpServer server;
	private String BASE_URI;
	private static int port= 8081;

	private HttpServer startGrizzlyServer() throws Exception{
		
		Db.PERSISTENCE_UNIT = Db.H2_UNIT;

		ResourceConfig rc = new ResourceConfig()
				.packages("br.com.margel.softvinhows");
		
		URI u = URI.create(BASE_URI = "http://localhost:"+(port++)+"/softvinho_test/");
		
		String path = u.getPath();
	    path = String.format("/%s", UriComponent.decodePath(u.getPath(), true)
	                 .get(1).toString());

	    WebappContext context = new WebappContext("GrizzlyContext", path);
	    context.addListener(DeployListener.class);
	    
	    Servlet servlet = new ServletContainer(rc);
	    
	    ServletRegistration registration = context.addServlet(servlet.getClass().getName(), servlet);
	    registration.addMapping("/*");

	    HttpServer server = GrizzlyHttpServerFactory.createHttpServer(u);
	    context.deploy(server);
	    return server;
	}
	
	@Before
	public void setUp() throws Exception {
		server = startGrizzlyServer();
		c = ClientBuilder.newClient();
		target = c.target(BASE_URI);
	}

	@After
	public void tearDown() throws Exception {
		c.close();
		server.shutdownNow();
	}
	
}
