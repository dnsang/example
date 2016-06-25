package com.sls.slsjtemplate.rest.handlers;

import com.sls.slsjtemplate.controllers.SampleController;
import com.sls.slsjtemplate.core.FlyweightFactory;
import com.sls.slsjtemplate.rest.param.DateParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/hello")
@Produces({"text/plain"})
public class SampleHandler {

    private static final SampleController _controller = FlyweightFactory.getInstance(SampleController.class);

    @GET
    public String hello(@QueryParam("name") String name, @DefaultValue("20060714") @QueryParam("time") DateParam time, @QueryParam("age") Integer age) {

        try {
            return _controller.sayHello(name);
        } catch (Exception ex) {
            return ex.toString();
        }
    }
}


/* Location:              /mnt/data/data/working/bitbucket/common/slsjtemplate/dist/jtemplate-1.0.jar!/com/sls/jtemplate/rest/handlers/SampleHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
