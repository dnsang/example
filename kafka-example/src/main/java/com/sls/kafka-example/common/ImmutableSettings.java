package com.sls.slsjtemplate.common;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.LinkedHashMap;
import java.util.Map;

public class ImmutableSettings
        implements Settings {

    public static final ImmutableSettings EMPTY = new ImmutableSettings();
    private final ImmutableMap<String, String> _options;

    private ImmutableSettings() {
        this._options = new ImmutableMap.Builder().build();
    }

    private ImmutableSettings(Builder builder) {
        this._options = ImmutableMap.copyOf(builder._options);
    }

    public String get(String key) {
        return (String) this._options.get(key);
    }

    public long getAsLong(String key, long defaultVal) {
        String get = (String) this._options.get(key);
        if (get == null) {
            return defaultVal;
        }
        try {
            return Long.parseLong(get);
        } catch (NumberFormatException nfe) {
        }
        return defaultVal;
    }

    public int getAsInteger(String key, int defaultVal) {
        String get = (String) this._options.get(key);
        if (get == null) {
            return defaultVal;
        }
        try {
            return Integer.parseInt(get);
        } catch (NumberFormatException nfe) {
        }
        return defaultVal;
    }

    public double getAsDouble(String key, double defaultVal) {
        String get = (String) this._options.get(key);
        if (get == null) {
            return defaultVal;
        }
        try {
            return Double.parseDouble(get);
        } catch (NumberFormatException nfe) {
        }
        return defaultVal;
    }

    public String[] getAsList(String key, String delimiter, String[] defaultVal) {
        if (!this._options.containsKey(key)) {
            return defaultVal;
        }
        String value = (String) this._options.get(key);
        return value.split(delimiter);
    }

    public int hashCode() {
        int temp = this._options != null ? this._options.hashCode() : 0;
        return 31 * temp;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        ImmutableSettings other = (ImmutableSettings) obj;

        return other._options == null ? true : this._options != null ? this._options.equals(other._options) : false;
    }

    public String getAsString(String key, String defaultVal) {
        String get = (String) this._options.get(key);
        if (get == null) {
            return defaultVal;
        }
        return get;
    }

    public static class Builder {

        private final Map<String, String> _options = new LinkedHashMap();

        public Builder put(String key, String value) {
            this._options.put(key, value);
            return this;
        }

        public Builder put(Map<String, String> options) {
            this._options.putAll(options);
            return this;
        }

        public ImmutableSettings build() {
            return new ImmutableSettings(this);
        }
    }
}


/* Location:              /mnt/data/data/working/bitbucket/common/slsjtemplate/dist/jtemplate-1.0.jar!/com/sls/jtemplate/common/ImmutableSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
