package cn.exsolo.basic.render;

import cn.exsolo.basic.org.po.OrgNodePO;
import cn.exsolo.batis.core.BaseDAO;
import cn.exsolo.batis.core.Condition;
import cn.exsolo.kit.cache.CacheEnum;
import cn.exsolo.kit.cache.IExCache;
import cn.exsolo.kit.cache.IExCacheStorage;
import cn.exsolo.kit.render.DataRender;
import cn.exsolo.kit.utils.ExPageWork;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author prestolive
 * @date 2024/11/7
 **/
@Component
public class OrgInfoDataRender implements DataRender {

    @Autowired
    private BaseDAO baseDAO;

    @Autowired
    private IExCache iExCache;

    private IExCacheStorage cacheStorage;

    private static final ThreadLocal<Map> HIT_MAP = new ThreadLocal<>();

    public IExCacheStorage getCacheStorage() {
        if (cacheStorage == null) {
            cacheStorage = iExCache.getCache(CacheEnum.DATA_RENDER_BASE);
        }
        return cacheStorage;
    }


    @Override
    public void preRender(Collection keyValues) {
        IExCacheStorage cs = getCacheStorage();
        Map hitMap = new HashMap();
        Set<String> toFixedSet = new HashSet<>();
        for (Object keyValue : keyValues) {
            Map row = cs.getMap(keyValue.toString());
            if (row != null) {
                hitMap.put(keyValue, row);
            } else {
                toFixedSet.add(keyValue.toString());
            }
        }
        if (toFixedSet.size() > 0) {
            queryAndCache(new ArrayList<>(toFixedSet), hitMap);
        }
        HIT_MAP.set(hitMap);
    }

    private void loopOrg(Map<String, OrgNodePO> parentMap,List<String> paths,OrgNodePO currNode){
        paths.add(currNode.getOrgName());
        if(StringUtils.isNotEmpty(currNode.getParentId())){
            OrgNodePO parent = parentMap.get(currNode.getParentId());
            if(parent!=null){
                loopOrg(parentMap,paths,parent);
            }
        }
    }

    private void queryAndCache(List<String> keyValues, Map hitMap) {
        IExCacheStorage cs = getCacheStorage();
        new ExPageWork().pageExecute(keyValues, 200, new ExPageWork.IPageExecute<String>() {
            @Override
            public void execute(List<String> list) {
                List<OrgNodePO> orgs = baseDAO.queryBeanByCond(OrgNodePO.class, new Condition().in("id", list));
                Set<String> codeSet = new HashSet<>();
                for (OrgNodePO org : orgs) {
                    String innerCode = org.getInnerCode();
                    for (int i = innerCode.length(); i >= 2; i = i - 2) {
                        codeSet.add(innerCode.substring(0,i));
                    }
                }
                List<OrgNodePO> parents =  baseDAO.queryBeanByCond(OrgNodePO.class, new Condition().in("innerCode", codeSet));
                Map<String, OrgNodePO> parentMap = new HashMap<>();
                for (OrgNodePO parent : parents) {
                    parentMap.put(parent.getId(),parent);
                }
                for(OrgNodePO org:orgs){
                    List<String> paths = new ArrayList<>();
                    loopOrg(parentMap,paths,org);
                    Collections.reverse(paths);
                    Map<String,Object> target = new HashMap<>();
                    target.put("name",org.getOrgName());
                    target.put("parentId",org.getParentId());
                    target.put("innerCode",org.getInnerCode());
                    target.put("childCounts",org.getChildCounts());
                    target.put("genus",org.getGenus());
                    target.put("fullName",StringUtils.join(paths,"/"));
                    target.put("paths",paths);
                    hitMap.put(org.getId(), target);
                }
            }
        });
    }

    @Override
    public Map<String, Object> getRenderFrame(Object keyValue, Map row) {
        Map hitMap = HIT_MAP.get();
        Map target = (Map) hitMap.get(keyValue);
        if (target != null) {
            return target;
        }
        return null;
    }
}
