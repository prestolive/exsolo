package cn.exsolo.kit.cache;

/**
 * @author prestolive
 * @date 2023/5/30
 **/
public interface IExCache {

    IExCacheStorage getCache(CacheEnum cacheEnum);

}
