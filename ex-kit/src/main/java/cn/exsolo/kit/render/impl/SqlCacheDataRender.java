package cn.exsolo.kit.render.impl;

import cn.exsolo.batis.core.BaseDAO;
import cn.exsolo.batis.core.CommonOrmUtils;
import cn.exsolo.batis.core.Condition;
import cn.exsolo.kit.cache.CacheEnum;
import cn.exsolo.kit.cache.IExCache;
import cn.exsolo.kit.cache.IExCacheStorage;
import cn.exsolo.kit.render.DataRender;
import cn.exsolo.kit.utils.ExPageWork;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author prestolive
 * @date 2024/3/6
 **/
public abstract class SqlCacheDataRender implements DataRender {

    @Autowired
    private BaseDAO baseDAO;

    @Autowired
    private IExCache iExCache;

    private static final ThreadLocal<Map> HIT_MAP = new ThreadLocal<>();

    public abstract String getSql();


    IExCacheStorage cacheStorage;


    public IExCacheStorage getCacheStorage() {
        if (cacheStorage == null) {
            cacheStorage = iExCache.getCache(CacheEnum.DATA_RENDER_BASE);
        }
        return cacheStorage;
    }

    private String getKey(String key) {
        return this.getClass().getName()+":"+key;
    }


    @Override
    public final void preRender(List<Pair<Object, Map>> pairList) {
        IExCacheStorage cs = getCacheStorage();
        List<String> keyValues = pairList.stream().map((row) -> row.getLeft().toString()).distinct().collect(Collectors.toList());
        Map hitMap = new HashMap();
        Set<String> toFixedSet = new HashSet<>();
        for (String keyValue : keyValues) {
            String cacheKey = getKey(keyValue);
            Map row = cs.getMap(cacheKey);
            if (row != null) {
                hitMap.put(cacheKey, row);
            } else {
                toFixedSet.add(keyValue);
            }
        }
        if (toFixedSet.size() > 0) {
            queryAndCache(new ArrayList<>(toFixedSet), hitMap);
        }
        HIT_MAP.set(hitMap);
    }

    private void queryAndCache(List<String> keyValues, Map hitMap) {
        IExCacheStorage cs = getCacheStorage();
        new ExPageWork().pageExecute(keyValues, 500, new ExPageWork.IPageExecute<String>() {
            @Override
            public void execute(List<String> list) {
                Condition cond = new Condition();
                cond.in("key", list);
                StringBuilder sql = new StringBuilder();
                sql.append("select * from (" + getSql() + ") t where 1=1");
                Map<String, Object> queryParamMap = new HashMap<>();
                CommonOrmUtils.generateConditionSql(sql, "t", cond, queryParamMap);
                List<Map> data = baseDAO.queryForList(sql.toString(), queryParamMap, Map.class);
                for (Map row : data) {
                    String key = getKey((String) row.get("key"));
                    row.remove("key");
                    //放入缓存
                    cs.pubMap(key, row);
                    hitMap.put(key, row);
                }
            }
        });
    }

    @Override
    public final Map<String, Object> getRenderFrame(Object keyValue, Map row) {
        Map hitMap = HIT_MAP.get();
        String key = getKey(String.valueOf(keyValue));
        Map target = (Map) hitMap.get(key);
        if (target != null) {
            return target;
        }
        return null;
    }
}
