package org.com.drag.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cdyoue on 2016/12/26.
 */
public class TreeCache {
    private static Map<String, Object> cacheMap;

    public static Object getCache(String key, Object defaultValue) {
        Object obj = getCacheMap().get(key);
        //Object obj = getSession().getAttribute(key);
        return obj==null?defaultValue:obj;
    }

    public static void putCache(String key, Object value) {
        getCacheMap().put(key, value);
        //getSession().setAttribute(key, value);
    }

    public static void removeCache(String key) {
        getCacheMap().remove(key);
        //getSession().removeAttribute(key);
    }


    public static Map<String, Object> getCacheMap() {
        if (cacheMap==null){
            cacheMap = new HashMap<String, Object>();
        }
        return cacheMap;
    }
}
