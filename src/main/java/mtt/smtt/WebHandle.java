package mtt.smtt;
 
import mtt.smtt.Application;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class WebHandle {
		
	public WebHandle() {
		System.out.println("Web handle's ready.");
	}

	public void up(){		
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        //Server jettyServer = new Server(8080);
        Server jettyServer = new Server(Application.getHttpPort());
        jettyServer.setHandler(context);
        ServletHolder jerseyServlet = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);
        jerseyServlet.setInitParameter("jersey.config.server.provider.classnames", EntryPoint.class.getCanonicalName());      
        try {
			jettyServer.start();
	        jettyServer.join();
        	} catch (Exception e) {	e.printStackTrace(); } 
        finally {
            jettyServer.destroy();
        	}
        boolean running = jettyServer.isRunning();
        System.out.println("Web handle running: " + running);
    }
}
