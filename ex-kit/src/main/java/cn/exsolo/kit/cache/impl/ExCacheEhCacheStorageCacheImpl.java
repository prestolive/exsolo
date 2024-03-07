package cn.exsolo.kit.cache.impl;

import cn.exsolo.kit.cache.IExCacheStorage;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import java.util.Map;

/**
 * @author prestolive
 * @date 2021/5/30
 **/
public class ExCacheEhCacheStorageCacheImpl implements IExCacheStorage {

    private Cache cache;

    public ExCacheEhCacheStorageCacheImpl(Cache cache) {
        this.cache = cache;
    }

    @Override
    public void putString(String key, String value) {
        Element element = new Element(key,value);
        this.cache.put(element);
    }

    @Override
    public String getString(String key) {
        Element element = this.cache.get(key);
        if(element!=null){
            return (String) element.getObjectValue();
        }
        return null;
    }

    @Override
    public void putStringArray(String key, String[] values) {
        Element element = new Element(key,values);
        this.cache.put(element);
    }

    @Override
    public String[] getStringArray(String key) {
        Element element = this.cache.get(key);
        if(element!=null){
            return (String[]) element.getObjectValue();
        }
        return null;
    }

    @Override
    public void putInt(String key, Integer value) {
        Element element = new Element(key,value);
        this.cache.put(element);
    }

    @Override
    public Integer getInt(String key) {
        Element element = this.cache.get(key);
        if(element!=null){
            return (Integer) element.getObjectValue();
        }
        return null;
    }

    @Override
    public void putBoolean(String key, boolean value) {
        Element element = new Element(key,value);
        this.cache.put(element);
    }

    @Override
    public boolean isTrue(String key) {
        Element element = this.cache.get(key);
        if(element!=null){
            return (Boolean) element.getObjectValue();
        }
        return false;
    }

    @Override
    public void remove(String key){
        this.cache.remove(key);
    }

    @Override
    public void pubMap(String key, Map map) {
        Element element = new Element(key,map);
        this.cache.put(element);
    }

    @Override
    public Map getMap(String key) {
        Element element = this.cache.get(key);
        if(element!=null){
            return (Map) element.getObjectValue();
        }
        return null;
    }
}
