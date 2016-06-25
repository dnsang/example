 package com.sls.slsjtemplate.rest.param;
 
 import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
 import org.joda.time.format.DateTimeFormatter;
 import org.joda.time.format.ISODateTimeFormat;
 
 
 
 
 
 
 
 
 
 
 
 public class DateParam
   extends RestParam<DateTime>
 {
   private static final DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/YYYY HH:mm:ss");
   
   public DateParam(String param) { super(param); }
   
   @Override
   protected DateTime parse(String param) throws Throwable
   {
     return dtf.parseDateTime(param);
   }
 }


/* Location:              /mnt/data/data/working/bitbucket/common/slsjtemplate/dist/jtemplate-1.0.jar!/com/sls/jtemplate/rest/param/DateParam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */