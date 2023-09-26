package cn.exsolo.kit.cache;

/**
 * @author prestolive
 * @date 2021/5/30
 **/
public interface IExCacheStorage {

    void putString(String key,String value);

    String getString(String key);

    void putStringArray(String key,String[] values);

    String[] getStringArray(String key);

    void putInt(String key,Integer value);

    Integer getInt(String key);

    void putBoolean(String key,boolean value);

    boolean isTrue(String key);

    void remove(String key);

}
