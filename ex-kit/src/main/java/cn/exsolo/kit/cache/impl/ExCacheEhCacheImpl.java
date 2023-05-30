package cn.exsolo.kit.cache.impl;

import cn.exsolo.kit.cache.CacheEnum;
import cn.exsolo.kit.cache.IExCache;
import cn.exsolo.kit.cache.IExCacheStorage;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author prestolive
 * @date 2023/5/30
 **/
public class ExCacheEhCacheImpl implements IExCache {

    private CacheManager cacheManager;

    public ExCacheEhCacheImpl(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    private ConcurrentMap<String,IExCacheStorage> map = new ConcurrentHashMap<>();


    @Override
    public IExCacheStorage getCache(CacheEnum cacheEnum) {
        String cacheName = cacheEnum.name();
        IExCacheStorage exCacheStorage = map.get(cacheName);
        if(exCacheStorage==null){
            Cache cache = cacheManager.getCache(cacheEnum.name());
            if(cache==null){
                CacheConfiguration configuration = new CacheConfiguration();
                configuration.setName(cacheName);
                configuration.maxElementsInMemory(10000);
                configuration.eternal(false);
                configuration.timeToIdleSeconds(120);
                configuration.timeToLiveSeconds(120);
                configuration.overflowToDisk(false);
                configuration.diskPersistent(false);
                configuration.diskExpiryThreadIntervalSeconds(120);
                //TODO
                cache =  new Cache(configuration);
                cacheManager.addCache(cache);
            }
            exCacheStorage = new ExCacheStorageCacheImpl(cache);
            map.put(cacheName,exCacheStorage);
        }
        return exCacheStorage;
    }
}
