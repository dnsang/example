package com.sls.slsjtemplate;

import com.sls.slsjtemplate.common.SettingFactory;
import com.sls.slsjtemplate.rest.RServer;
import com.sls.slsjtemplate.thrift.TServers;
import java.io.PrintStream;

public class MainApp {

    public static void main(String[] args) {
        SettingFactory.init(args);

        RServer rServer = new RServer();
        if (!rServer.setupAndStart()) {
            System.err.println("Could not start thrift servers! Exit now.");
            System.exit(1);
        }

//        TServers tServers = new TServers();
//        if (!tServers.setupAndStart()) {
//            System.err.println("Could not start thrift servers! Exit now.");
//            System.exit(1);
//        }
    }
}


/* Location:              /mnt/data/data/working/bitbucket/common/slsjtemplate/dist/jtemplate-1.0.jar!/com/sls/jtemplate/MainApp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
