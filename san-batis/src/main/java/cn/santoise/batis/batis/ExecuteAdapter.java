package cn.santoise.batis.batis;

import cn.santoise.batis.core.PageObject;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wuby on 2018/7/25.
 */
@Service
public class ExecuteAdapter {

    @Autowired
    private AdapterMapper adapterMapper;

    public <T> List<T> executeQuery(String sql, Map<String,Object> values, Class<T> clz){
        Map<String,Object> req = new HashMap<>();
        req.put("sql",sql);
        req.put("resultType",clz);
        if(values!=null){
            req.putAll(values);
        }
        return adapterMapper.executeQuery(req);
    }

    public <T> PageObject<T> executeQueryPage(String sql, Map<String,Object> values, Class<T> clz, Integer pageRow, Integer currIdx){
        final Map<String,Object> req = new HashMap<>();
        req.put("sql",sql);
        req.put("resultType",clz);
        if(values!=null){
            req.putAll(values);
        }
        Page<T> page = PageHelper.startPage(currIdx, pageRow).doSelectPage(new ISelect() {
            @Override
            public void doSelect() {
                adapterMapper.executeQuery(req);
            }
        });
        //如果使用上述方法，将取不到数据，原因不详
//        PageHelper.startPage(currIdx, pageRow);
//        PageInfo<T> page = new PageInfo<>(adapterMapper.executeQuery(req));

        PageObject result = new PageObject();
        result.setValues(page.getResult());
        result.setCurrent(currIdx);
        result.setPageSize(pageRow);
        result.setPages(Long.valueOf(page.getPages()));
        result.setTotal(page.getTotal());
        return result;
    }

    public int executeUpdate(String sql,Map<String,Object> values){
        Map<String,Object> req = new HashMap<>();
        req.put("sql",sql);
        req.put("resultType",Integer.class);
        if(values!=null){
            req.putAll(values);
        }
        return adapterMapper.executeUpdate(req);
    }

    public int executeUpdateForBatch(String head,String body,List<Map<String,Object>> list){
        Map<String,Object> req = new HashMap<>();
        req.put("head",head);
        req.put("body",body);
        req.put("resultType",Integer.class);
        if(list!=null){
            req.put("list",list);
        }
        return adapterMapper.executeUpdateBatch(req);
    }


}
