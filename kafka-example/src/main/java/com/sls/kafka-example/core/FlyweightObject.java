 package com.sls.slsjtemplate.core;
 
 import com.sls.slsjtemplate.common.Settings;
 import org.apache.commons.lang3.builder.ToStringBuilder;
 
 
 
 
 
 
 
 
 public abstract class FlyweightObject
 {
   protected Settings _settings;
   
   public FlyweightObject(Settings settings)
   {
     this._settings = settings;
   }
   
   public String toString()
   {
     return ToStringBuilder.reflectionToString(this);
   }
 }


/* Location:              /mnt/data/data/working/bitbucket/common/slsjtemplate/dist/jtemplate-1.0.jar!/com/sls/jtemplate/core/FlyweightObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */