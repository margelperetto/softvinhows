package br.com.margel.softvinhows;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class DeployListener implements ServletContextListener {   
   
    @Override
	public void contextInitialized(ServletContextEvent ev) {
    	System.out.println("\nCONTEXTO INICIALIZADO!");
    	Db.createFactory();
    }
       
    @Override
	public void contextDestroyed(ServletContextEvent ev) {
    	Db.closeFactory();
    	System.out.println("CONTEXTO DESTRUIDO!\n");
    }
    
}