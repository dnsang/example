 package com.sls.slsjtemplate.common;
 
 import java.util.HashMap;
 import java.util.Map;
 import org.apache.log4j.Logger;
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 public class XLogger
 {
   private static final Map<String, Logger> _customLoggers = new HashMap();
   
 
 
 
 
   public static synchronized Logger getXLogger()
   {
     return Logger.getLogger(XLogger.class);
   }
   
   public static synchronized Logger getXLogger(Class cls) { return Logger.getLogger(cls); }
 }


/* Location:              /mnt/data/data/working/bitbucket/common/slsjtemplate/dist/jtemplate-1.0.jar!/com/sls/jtemplate/common/XLogger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */