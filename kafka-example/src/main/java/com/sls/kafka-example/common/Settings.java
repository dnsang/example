package com.sls.slsjtemplate.common;

public abstract interface Settings
{
  public abstract String get(String paramString);
  
  public abstract String getAsString(String paramString1, String paramString2);
  
  public abstract long getAsLong(String paramString, long paramLong);
  
  public abstract int getAsInteger(String paramString, int paramInt);
  
  public abstract double getAsDouble(String paramString, double paramDouble);
  
  public abstract String[] getAsList(String paramString1, String paramString2, String[] paramArrayOfString);
}


/* Location:              /mnt/data/data/working/bitbucket/common/slsjtemplate/dist/jtemplate-1.0.jar!/com/sls/jtemplate/common/Settings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */