package com.sls.slsjtemplate.thrift;

import com.sls.slsjtemplate.common.Settings;
import com.sls.slsjtemplate.common.XLogger;
import com.sls.slsjtemplate.core.FlyweightObject;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

public class ThriftServer
        extends FlyweightObject {

    public String host = "0.0.0.0";
    protected int port = 1;
    protected int ncoreThreads = 16;
    protected int nmaxThreads = this.ncoreThreads * 2;
    protected int maxWorkQueueSize = 0;

    protected int maxFrameSize = 65536;
    protected int totalFrameSize = 1073741824;

    protected TThreadedSelectorServer.Args.AcceptPolicy acceptPolicy = TThreadedSelectorServer.Args.AcceptPolicy.FAST_ACCEPT;
    protected int acceptQueueSizePerThread = 64;
    protected int nselectorThreads = 8;
    public static final Logger _logger = XLogger.getXLogger();
    protected final String _name;
    protected TServer _server;

    public ThriftServer(Settings settings) {
        super(settings);
        this.host = settings.getAsString("host", "0.0.0.0");
        this.port = settings.getAsInteger("port", 8080);
        this.ncoreThreads = settings.getAsInteger("ncoreThreads", 8);
        this.nmaxThreads = settings.getAsInteger("nmaxThreads", this.ncoreThreads * 2);
        this.maxWorkQueueSize = settings.getAsInteger("maxWorkQueueSize", this.maxWorkQueueSize);
        this.maxFrameSize = settings.getAsInteger("maxFrameSize", this.maxFrameSize);
        this.totalFrameSize = settings.getAsInteger("totalFrameSize", this.totalFrameSize);
        this.acceptQueueSizePerThread = settings.getAsInteger("acceptQueueSizePerThread", this.acceptQueueSizePerThread);
        this.nselectorThreads = settings.getAsInteger("nselectorThreads", this.nselectorThreads);
        this._name = "ThriftServer";
    }

    public void init() {
    }

    public boolean setup(TProcessor processor) {
        if (this._server != null) {
            _logger.warn("Server was already setup, dont need to setup again");
            return true;
        }
        try {
            InetAddress inetAddr = InetAddress.getByName(this.host);
            TServerSocket socket = new TServerSocket(this.port);
            TThreadPoolServer.Args options = new TThreadPoolServer.Args(socket);

            LinkedBlockingQueue<Runnable> workQueue;
            if (this.maxWorkQueueSize > 0) {
                workQueue = new LinkedBlockingQueue(this.maxWorkQueueSize);
            } else {
                workQueue = new LinkedBlockingQueue();
            }
            int stopTimeoutVal = options.stopTimeoutVal;
            TimeUnit stopTimeoutUnit = options.stopTimeoutUnit;
            ThreadPoolExecutor executor = new ThreadPoolExecutor(this.ncoreThreads, this.nmaxThreads, stopTimeoutVal, stopTimeoutUnit, workQueue);

            options.executorService(executor);
            options.processor(processor);
            options.transportFactory(new TFramedTransport.Factory());
            options.protocolFactory(new TBinaryProtocol.Factory(false, true));

            this._server = new TThreadPoolServer(options);
            _logger.info(String.format("Thrift server (TThreadPoolServer) listens on %s:%s", new Object[]{inetAddr.getHostAddress(), Integer.valueOf(this.port)}));
        } catch (UnknownHostException | TTransportException ex) {
            _logger.error(null, ex);
            return false;
        }
        return true;
    }

    public boolean start() {
        if (this._server == null) {
            return false;
        }
        Thread thread = new Thread(new ServerRunner(this._server), this._name);

        thread.start();
        return true;
    }

    public void stop() {
        if (this._server == null) {
            return;
        }
        this._server.stop();
    }

    protected class ServerRunner implements Runnable {

        private final TServer _server;

        public ServerRunner(TServer server) {
            this._server = server;
        }

        public void run() {
            ThriftServer._logger.info("Thrift server is going to serve");
            try {
                this._server.serve();
            } catch (Exception ex) {
                ThriftServer._logger.error(null, ex);
            }
            ThriftServer._logger.info("Thrift server stopped");
        }
    }
}


/* Location:              /mnt/data/data/working/bitbucket/common/slsjtemplate/dist/jtemplate-1.0.jar!/com/sls/jtemplate/thrift/ThriftServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
