 package com.sls.slsjtemplate.rest.param;
 
 import com.sls.slsjtemplate.common.XLogger;
 import javax.ws.rs.core.Response;
 import javax.ws.rs.core.Response.ResponseBuilder;
 import javax.ws.rs.core.Response.Status;
 import org.apache.log4j.Logger;
 
 
 
 
 
 
 
 public abstract class RestParam<T>
 {
   protected T _value;
   protected final String _param;
   
   public T getValue()
   {
     return (T)this._value;
   }
   
 
 
 
 
   public RestParam(String param)
   {
     this._param = param;
     try {
       this._value = parse(this._param);
     } catch (Throwable t) {
       XLogger.getXLogger(RestParam.class).error(t.getMessage());
       this._value = null;
     }
   }
   
 
 
 
   protected abstract T parse(String paramString)
     throws Throwable;
   
 
 
   protected Response parseParamErrorResponse(String param, Throwable t)
   {
     String resp = "Fail to parse " + param + " to " + this._value.getClass().getName() + " error: " + t.getMessage();
     
 
     return Response.status(Response.Status.EXPECTATION_FAILED).entity(resp).build();
   }
   
   public String toString()
   {
     return this._value != null ? this._value.toString() : this._param;
   }
 }


/* Location:              /mnt/data/data/working/bitbucket/common/slsjtemplate/dist/jtemplate-1.0.jar!/com/sls/jtemplate/rest/param/RestParam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */