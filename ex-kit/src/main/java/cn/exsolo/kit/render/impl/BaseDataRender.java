package cn.exsolo.kit.render.impl;


import cn.exsolo.kit.ex.EsBuilderException;
import cn.exsolo.kit.render.IDataRender;

import java.util.*;

/**
 * Created by prestolive on 2017/7/28.
 * @Author prestolive
 */
public abstract class BaseDataRender implements IDataRender {

    private String path;

    private String keyField;

    public BaseDataRender(String path, String keyField) {
        this.path=path;
        this.keyField=keyField;
    }

    @Override
    public String getTargetPath() {
        return path;
    }

    @Override
    public void pack(Object target) throws EsBuilderException {
        //
        Set<String> keys = new HashSet<>();
        List<Map<String, Object>> rows = new ArrayList();
        if(target instanceof List){
            List<Map<String, Object>> listData = (List<Map<String, Object>>) target;
            if (listData.size() == 0){
                return;
            }
            for (Map<String, Object> item : listData) {
                keys.add((String) item.get(keyField));
                rows.add(item);
            }
        }else{
            if(target==null){
                return;
            }
            Map<String, Object> item = (Map<String, Object>) target;
            keys.add((String) item.get(keyField));
            rows.add(item);
        }
        if(keys.size()==0){
            return;
        }
        //渲染前事件
        handleKeysBeforeRender(rows,keys);
        //开始渲染
        for (Map<String, Object> row : rows) {
            String key = (String) row.get(keyField);
            //触发单行渲染事件
            handleRowRender(row,key);
        }
    }

    /**
     * 在做最终渲染前，将keys触发个事件，开发者可以在这个方法统一查询和缓存数据，为后面的单行渲染提供数据
     * @param rows 由原目标数据转成的list数据，地址引用，渲染后原目标格式不变，仅叠加了渲染的数据
     * @param keys
     */
    abstract protected void handleKeysBeforeRender(List<Map<String, Object>> rows ,Set<String> keys);

    /**
     * 单行渲染事件
     * @param row
     * @param key
     */
    abstract protected void handleRowRender(Map<String, Object> row ,String key);


}
