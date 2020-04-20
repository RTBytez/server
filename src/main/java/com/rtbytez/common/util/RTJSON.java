package com.rtbytez.common.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Locale;
import java.util.Map;

/**
 * JSONObject but with added functions
 */
public class RTJSON extends JSONObject {

    public RTJSON() {
        super();
    }

    public RTJSON(JSONObject jo, String[] names) {
        super(jo, names);
    }

    public RTJSON(JSONTokener x) throws JSONException {
        super(x);
    }

    public RTJSON(Map<?, ?> m) {
        super(m);
    }

    public RTJSON(Object bean) {
        super(bean);
    }

    public RTJSON(Object object, String[] names) {
        super(object, names);
    }

    public RTJSON(String source) throws JSONException {
        super(source);
    }

    public RTJSON(String baseName, Locale locale) throws JSONException {
        super(baseName, locale);
    }

    public RTJSON(int initialCapacity) {
        super(initialCapacity);
    }

    public Object get(String key, Object defaultValue) {
        try {
            return super.get(key);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    public <E extends Enum<E>> E getEnum(Class<E> clazz, String key, E defaultValue) {
        try {
            return super.getEnum(clazz, key);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        try {
            return super.getBoolean(key);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    public BigInteger getBigInteger(String key, BigInteger defaultValue) {
        try {
            return super.getBigInteger(key);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    public BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
        try {
            return super.getBigDecimal(key);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    public double getDouble(String key, Double defaultValue) {
        try {
            return super.getDouble(key);
        } catch (JSONException e) {
            return defaultValue;
        }
    }


    public float getFloat(String key, float defaultValue) {
        try {
            return super.getFloat(key);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    public Number getNumber(String key, Number defaultValue) {
        try {
            return super.getNumber(key);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    public int getInt(String key, int defaultValue) {
        try {
            return super.getInt(key);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    public JSONArray getJSONArray(String key, JSONArray defaultValue) {
        try {
            return super.getJSONArray(key);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    public JSONObject getJSONObject(String key, JSONObject defaultValue) {
        try {
            return super.getJSONObject(key);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    public long getLong(String key, long defaultValue) {
        try {
            return super.getLong(key);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    public String getString(String key, String defaultValue) {
        try {
            return super.getString(key);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    public boolean has(String... keys) {
        for (String key : keys) {
            if (!this.has(key)) {
                return false;
            }
        }
        return true;
    }
}
