 package com.sls.slsjtemplate.rest;
 
 import com.sls.slsjtemplate.common.Settings;
 import com.sls.slsjtemplate.common.XLogger;
 import com.sls.slsjtemplate.core.FlyweightObject;
 import java.net.BindException;
 import org.apache.log4j.Logger;
 import org.eclipse.jetty.server.Connector;
 import org.eclipse.jetty.server.Server;
 import org.eclipse.jetty.server.nio.SelectChannelConnector;
 import org.eclipse.jetty.servlet.ServletContextHandler;
 import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
 
/**
 *
 * @author sangdn
 */
public class JettyServer
   extends FlyweightObject
 {
   protected String _host = "0.0.0.0";
   protected int _port = 8080;
   protected int _nconnectors = 1;
   protected int _nacceptors = 4;
   protected int _acceptQueueSize = 500;
   protected int _nminThreads = 10;
   protected int _nmaxThreads = this._nminThreads * 2;
   protected int _maxIdleTime = 5000;
   protected int _connMaxIdleTime = this._maxIdleTime;
   protected int _threadMaxIdleTime = this._maxIdleTime;
   protected Server _server = null;
   protected Thread _thread;
   protected Logger _logger = XLogger.getXLogger();
   
   public JettyServer(Settings settings) {
     super(settings);
     this._port = settings.getAsInteger("port", this._port);
     this._nacceptors = settings.getAsInteger("nacceptors", this._nacceptors);
     this._acceptQueueSize = settings.getAsInteger("queue-size", this._acceptQueueSize);
     this._nminThreads = settings.getAsInteger("min-thread", this._nminThreads);
     this._nmaxThreads = settings.getAsInteger("max-thread", this._nminThreads*2);
     this._maxIdleTime = settings.getAsInteger("max-idle-time", this._maxIdleTime);
     init();
   }
   
   protected final void init() {
     this._server = new Server();
     QueuedThreadPool _threadPool = new QueuedThreadPool();
     _threadPool.setName("JettyThreadPoolServer");
     _threadPool.setMinThreads(this._nminThreads);
     _threadPool.setMaxThreads(this._nmaxThreads);
     _threadPool.setMaxIdleTimeMs(this._maxIdleTime);
     this._server.setThreadPool(_threadPool);
     Connector[] connectors = new Connector[this._nconnectors];
     for (int i = 0; i < this._nconnectors; i++) {
       SelectChannelConnector connector1 = new SelectChannelConnector();
       connector1.setHost(this._host);
       connector1.setPort(this._port + i);
       connector1.setMaxIdleTime(this._connMaxIdleTime);
       connector1.setAcceptQueueSize(this._acceptQueueSize);
       connector1.setThreadPool(_threadPool);
       connector1.setAcceptors(this._nacceptors);
       connectors[i] = connector1;
     }
     this._server.setConnectors(connectors);
   }
   
   public boolean start()
   {
     if (this._server == null) {
       return false;
     }
     
     boolean result = false;
     try {
       this._server.start();
       this._thread = new Thread(new ServerRunner(this._server), "JettyWebServerRunner");
       this._thread.start();
       result = true;
     } catch (BindException ex) {
       this._logger.error(null, ex);
       stop();
     } catch (Exception ex) {
       this._logger.error(null, ex);
       stop();
     }
     return result;
   }
   
   public boolean stop() {
     try {
       this._server.stop();
       this._thread.join();
       this._thread = null;
       return true;
     } catch (Exception ex) {
       this._logger.error(null, ex); }
     return false;
   }
   
 
   public void setup(ServletContextHandler handler)
   {
     this._server.setHandler(handler);
   }
   
   protected class ServerRunner implements Runnable
   {
     private final Server _server;
     
     public ServerRunner(Server server) {
       this._server = server;
     }
     
     public void run()
     {
       JettyServer.this._logger.info("Web server is going to serve");
       try {
         this._server.join();
       } catch (Exception ex) {
         JettyServer.this._logger.error(null, ex);
       }
       JettyServer.this._logger.info("Web Server is going to stopped");
     }
   }
 }


/* Location:              /mnt/data/data/working/bitbucket/common/slsjtemplate/dist/jtemplate-1.0.jar!/com/sls/jtemplate/rest/JettyServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */