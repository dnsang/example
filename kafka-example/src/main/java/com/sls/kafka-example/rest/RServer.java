 package com.sls.slsjtemplate.rest;
 
 import com.sls.slsjtemplate.common.SettingFactory;
 import com.sls.slsjtemplate.core.FlyweightFactory;
 import com.sls.slsjtemplate.rest.filters.CORSResponseFilter;
import com.sls.slsjtemplate.rest.filters.SessionRequestFilter;
import com.sls.slsjtemplate.rest.filters.SessionResponseFilter;
 import org.eclipse.jetty.servlet.ServletContextHandler;
 import org.eclipse.jetty.servlet.ServletHolder;
 import org.glassfish.jersey.server.ResourceConfig;
 import org.glassfish.jersey.servlet.ServletContainer;
 
 
 
 
 
 
 
 
 
 
 public class RServer
 {
   public boolean setupAndStart()
   {
     JettyServer server = (JettyServer)FlyweightFactory.getInstance(JettyServer.class, SettingFactory.get("WebServer"));
     
     ResourceConfig resourceConfig = new ResourceConfig();
     resourceConfig.register(CORSResponseFilter.class);
     resourceConfig.register(SessionRequestFilter.class);
     resourceConfig.register(SessionResponseFilter.class);
     resourceConfig.packages(new String[] { "com.sls.slsjtemplate.rest.handlers" });
     
     ServletHolder servletHolder = new ServletHolder(new ServletContainer(resourceConfig));
     ServletContextHandler handlers = new ServletContextHandler();
     handlers.setContextPath("/");
     handlers.addServlet(servletHolder, "/*");
     server.setup(handlers);
     return server.start();
   }
 }


/* Location:              /mnt/data/data/working/bitbucket/common/slsjtemplate/dist/jtemplate-1.0.jar!/com/sls/jtemplate/rest/RServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */