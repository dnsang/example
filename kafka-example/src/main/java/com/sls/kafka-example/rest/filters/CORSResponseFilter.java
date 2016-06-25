 package com.sls.slsjtemplate.rest.filters;
 
 import java.io.IOException;
 import javax.ws.rs.container.ContainerRequestContext;
 import javax.ws.rs.container.ContainerResponseContext;
 import javax.ws.rs.container.ContainerResponseFilter;
 import javax.ws.rs.core.MultivaluedMap;
 
 
 
 
 
 
 
 
 
 public class CORSResponseFilter
   implements ContainerResponseFilter
 {
   public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
     throws IOException
   {
     MultivaluedMap<String, Object> headers = responseContext.getHeaders();
     headers.add("Access-Control-Allow-Origin", "*");
     headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
     headers.add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, X-Requested-With, X-Codingpedia");
     
   }
 }


/* Location:              /mnt/data/data/working/bitbucket/common/slsjtemplate/dist/jtemplate-1.0.jar!/com/sls/jtemplate/rest/filters/CORSResponseFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */