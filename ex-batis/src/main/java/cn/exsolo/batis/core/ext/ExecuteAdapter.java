package cn.exsolo.batis.core.ext;

import cn.exsolo.batis.core.PageObject;
import cn.exsolo.batis.core.Pagination;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by prestolive on 2018/7/25.
 */
@Service
public class ExecuteAdapter {

    @Autowired
    private AdapterMapper adapterMapper;

    public <T> List<T> executeQuery(String sql, Map<String, Object> values, Class<T> clz) {
        Map<String, Object> req = new HashMap<>();
        req.put("sql", sql);
        req.put("resultType", clz);
        if (values != null) {
            req.putAll(values);
        }
        List<T> list = adapterMapper.executeQuery(req);
        if (list.size() > 0 && Map.class.isAssignableFrom(clz)) {
            //如果是Map对象则尝试将数据库字段转成对象的字段（全小写转驼峰）
            Map<String, String> tokenMap = getTokenMap(sql);
            if (tokenMap != null && tokenMap.size() > 0) {
                for (T t : list) {
                    Map row = (Map) t;
                    List<String> keys = new ArrayList<>(row.keySet());
                    List<String> removeKeys = new ArrayList<>();
                    for (Object key : keys) {
                        String originKey = tokenMap.get(key.toString().toLowerCase(Locale.ROOT));
                        if (StringUtils.isEmpty(originKey)) {
                            continue;
                        }
                        row.put(originKey, row.get(key));
                        if(!originKey.equals(key.toString())){
                            removeKeys.add(key.toString());
                        }
                    }
                    for(String removeKey : removeKeys){
                        row.remove(removeKey);
                    }
                }
            }
        }
        return list;
    }

    private static Map<String, String> getTokenMap(String sql) {
        // 去除所有标点符号（这里假设标点符号包括常见的英文标点符号）
        String cleanedText = sql.replaceAll("[\\p{Punct}]+", " ");
        // 将处理后的字符串按空格分割成单词数组
        String[] words = cleanedText.trim().split("\\s+");
        // 打印结果
        Map<String, String> tokenMap = new HashMap<>();
        // 如果需要过滤掉空字符串（由连续标点符号导致）
        Arrays.stream(words)
                .filter(word -> !word.isEmpty())
                .forEach(word -> {
                    String key = word.toLowerCase(Locale.ROOT);
                    //只取第一次出现的，越靠前越准确
                    if(!tokenMap.containsKey(key)){
                        tokenMap.put(key, word);
                    }
                });
        return tokenMap;
    }

    public static void main(String[] args) {
        String sql = "SELECT * from (SELECT id as keyValue,loginCode,userName,phone,email from ex_user) t where 1=1 and keyValue in ('')";

    }

    public <T> PageObject<T> executeQueryPage(String sql, Map<String, Object> values, Class<T> clz, Integer pageRow, Integer currIdx) {
        final Map<String, Object> req = new HashMap<>();
        req.put("sql", sql);
        req.put("resultType", clz);
        if (values != null) {
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
        Pagination pagination = new Pagination(page.getTotal(), page.getPages(), currIdx, pageRow);
        result.setPagination(pagination);
        return result;
    }

    public int executeUpdate(String sql, Map<String, Object> values) {
        Map<String, Object> req = new HashMap<>();
        req.put("sql", sql);
        req.put("resultType", Integer.class);
        if (values != null) {
            req.putAll(values);
        }
        return adapterMapper.executeUpdate(req);
    }

    public int executeUpdateForBatch(String head, String body, List<Map<String, Object>> list) {
        Map<String, Object> req = new HashMap<>();
        req.put("head", head);
        req.put("body", body);
        req.put("resultType", Integer.class);
        if (list != null) {
            req.put("list", list);
        }
        return adapterMapper.executeUpdateBatch(req);
    }


}
