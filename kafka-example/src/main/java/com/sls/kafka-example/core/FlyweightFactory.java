 package com.sls.slsjtemplate.core;
 
 import com.sls.slsjtemplate.common.ImmutableSettings;
 import com.sls.slsjtemplate.common.Settings;
 import com.sls.slsjtemplate.common.XLogger;
 import java.lang.reflect.Constructor;
 import java.lang.reflect.InvocationTargetException;
 import java.util.Map;
 import java.util.concurrent.ConcurrentHashMap;
 import org.apache.log4j.Logger;
 
 
 
 
 
 
 
 
 
 
 
 
 
 public class FlyweightFactory
 {
   protected static final Map<Class, Map<Settings, FlyweightObject>> _mapControllers = new ConcurrentHashMap();
   
 
 
 
 
 
   public static <T> T getInstance(Class<T> cls)
   {
     return (T)getInstance(cls, ImmutableSettings.EMPTY);
   }
   
 
 
 
 
 
 
   public static <T> T getInstance(Class<T> cls, Settings settings)
   {
     assert (settings != null);
     if (!_mapControllers.containsKey(cls)) {
       synchronized (_mapControllers) {
         if (!_mapControllers.containsKey(cls)) {
           _mapControllers.put(cls, new ConcurrentHashMap());
         }
       }
     }
     Map<Settings, FlyweightObject> _classInst = (Map)_mapControllers.get(cls);
     if (!_classInst.containsKey(settings)) {
       synchronized (cls.getClass()) {
         if (!_classInst.containsKey(settings)) {
           try {
             if (FlyweightObject.class.isAssignableFrom(cls)) {
               Constructor<T> constructor = cls.getConstructor(new Class[] { Settings.class });
               _classInst.put(settings, (FlyweightObject)constructor.newInstance(new Object[] { settings }));
             } else {
               throw new IllegalArgumentException(cls.getName() + " is not assignable from BaseController");
             }
           } catch (NoSuchMethodException|SecurityException|InstantiationException|IllegalAccessException|IllegalArgumentException|InvocationTargetException ex) {
             XLogger.getXLogger(FlyweightFactory.class).error(ex);
           }
         }
       }
     }
     return (T)_classInst.get(settings);
   }
 }


/* Location:              /mnt/data/data/working/bitbucket/common/slsjtemplate/dist/jtemplate-1.0.jar!/com/sls/jtemplate/core/FlyweightFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */