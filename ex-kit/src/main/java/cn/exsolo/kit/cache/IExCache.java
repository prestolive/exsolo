package cn.exsolo.kit.cache;

/**
 * @author prestolive
 * @date 2021/5/30
 **/
public interface IExCache {

    IExCacheStorage getCache(CacheEnum cacheEnum);

}
