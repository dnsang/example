/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sls.slsjtemplate.rest.filters;

import com.sls.slsjtemplate.core.SessionHolder;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

/**
 *
 * @author sangdn
 */
public class SessionRequestFilter implements ContainerRequestFilter{
    @Override
    public void filter(ContainerRequestContext crc) throws IOException {
        
    }
    
}
