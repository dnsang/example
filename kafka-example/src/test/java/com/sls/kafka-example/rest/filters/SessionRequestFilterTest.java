/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sls.slsjtemplate.rest.filters;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_COLOR_BURNPeer;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.eclipse.jetty.jndi.java.javaNameParser;
import org.junit.Test;

/**
 *
 * @author sangdn
 */
public class SessionRequestFilterTest {

    public static void main() {
//        singleRequest(100);
    }

//    @Test
    public void singleRequest() {
        int n = 100;
        Client cli = ClientBuilder.newClient();
        WebTarget target = cli.target("http://localhost:8084/hello");
        for (int i = 0; i < n; ++i) {
            target.request().get();
        }
    }

//    @Test
    public void multiRequest() {
        final int nRequest = 100;
        int nThread = 10;
        Thread[] threads = new Thread[nThread];
        final Set<String> ssIds = new java.util.concurrent.ConcurrentSkipListSet<>();
        for (int i = 0; i < nThread; ++i) {
            threads[i] = new Thread(new Runnable() {

                @Override
                public void run() {
                    Client cli = ClientBuilder.newClient();
                    WebTarget target = cli.target("http://localhost:8084/hello");
                    for (int i = 0; i < nRequest; ++i) {
                        Response get = target.request().get();
                        String ssId = String.valueOf(get.getEntity());
                        if(ssIds.contains(ssId)){
                            System.out.println("Failed At:"+ssId);
                        }else{
                            ssIds.add(ssId);
                        }
                    }
                }
            });            
        }
        for (int i = 0; i < nThread; i++) {
            threads[i].start();
        }
        for (int i = 0; i < nThread; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException ex) {
                Logger.getLogger(SessionRequestFilterTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //verify after done
        
    }
}
