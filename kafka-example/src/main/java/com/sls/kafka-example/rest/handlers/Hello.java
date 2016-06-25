 package com.sls.slsjtemplate.rest.handlers;
 
 import com.sls.slsjtemplate.controllers.ArithmeticController;
 import com.sls.slsjtemplate.core.FlyweightFactory;
 import javax.ws.rs.GET;
 import javax.ws.rs.Path;
 import javax.ws.rs.QueryParam;
 
 
 
 
 
 
 
 
 
 
 @Path("/sum")
 public class Hello
 {
   @GET
   public String getProcess(@QueryParam("value1") int value1, @QueryParam("value2") int value2)
   {
     return String.valueOf(((ArithmeticController)FlyweightFactory.getInstance(ArithmeticController.class)).sum(value1, value2));
   }
 }


/* Location:              /mnt/data/data/working/bitbucket/common/slsjtemplate/dist/jtemplate-1.0.jar!/com/sls/jtemplate/rest/handlers/Hello.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */