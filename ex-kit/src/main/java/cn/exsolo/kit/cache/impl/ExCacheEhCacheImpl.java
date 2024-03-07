package cn.exsolo.kit.cache.impl;

import cn.exsolo.kit.cache.CacheEnum;
import cn.exsolo.kit.cache.IExCache;
import cn.exsolo.kit.cache.IExCacheStorage;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author prestolive
 * @date 2021/5/30
 **/
public class ExCacheEhCacheImpl implements IExCache {

    private CacheManager cacheManager;

    public ExCacheEhCacheImpl(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    private ConcurrentMap<String, IExCacheStorage> map = new ConcurrentHashMap<>();


    @Override
    public IExCacheStorage getCache(CacheEnum cacheEnum) {
        String cacheName = cacheEnum.name();
        IExCacheStorage exCacheStorage = map.get(cacheName);
        if (exCacheStorage == null) {
            exCacheStorage = createCache(cacheEnum);
        }
        return exCacheStorage;
    }

    private synchronized IExCacheStorage createCache(CacheEnum cacheEnum) {
        IExCacheStorage exCacheStorage;
        Cache cache = cacheManager.getCache(cacheEnum.name());
        if (cache == null) {
            CacheConfiguration configuration = new CacheConfiguration();
            configuration.setName(cacheEnum.name());
            configuration.maxElementsInMemory(10000);
            configuration.eternal(false);
            configuration.timeToIdleSeconds(cacheEnum.getExpireTimes()/2);
            configuration.timeToLiveSeconds(cacheEnum.getExpireTimes());
            configuration.overflowToDisk(false);
            configuration.diskPersistent(false);
            configuration.diskExpiryThreadIntervalSeconds(120);
            if(cacheManager.cacheExists(cacheEnum.name())){
                cache = cacheManager.getCache(cacheEnum.name());
            }else{
                cache = new Cache(configuration);
                cacheManager.addCache(cache);
            }
        }
        exCacheStorage = new ExCacheEhCacheStorageCacheImpl(cache);
        map.put(cacheEnum.name(), exCacheStorage);
        return exCacheStorage;
    }
}
