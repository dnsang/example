/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sls.slsjtemplate.rest.filters;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

/**
 *
 * @author sangdn
 */
public class SessionResponseFilter implements ContainerResponseFilter{
    
    @Override
    public void filter(ContainerRequestContext crc, ContainerResponseContext crc1) throws IOException {
        
        System.out.println(Thread.currentThread().getName());
    }
    
}
