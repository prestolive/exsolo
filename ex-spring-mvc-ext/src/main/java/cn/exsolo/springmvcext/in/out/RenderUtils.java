package cn.exsolo.springmvcext.in.out;

import cn.exsolo.kit.render.DataRender;
import cn.exsolo.kit.render.stereotype.DataRenderProvider;
import cn.hutool.core.bean.BeanUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 文件描述
 *
 * @author prestolive
 * @version 1.0
 * @date 2025/4/16 9:34
 * Copyright © 2025 厦门象屿股份有限公司. All Rights Reserved
 **/
public class RenderUtils {

    public static Map data2map(Object obj){
        return  BeanUtil.beanToMap(obj);
    }

    public static void render(Map respMap,String path,String keyField, DataRender render,String defaultAlias,String wapperType){
        //查出数据行
        List<Map> targetRows = new ArrayList<>();
        //路径栈
        Stack<String> stack = new Stack<>();
        stack.add("data");
        List<String> paths= Arrays.stream(path.split("\\.")).filter(StringUtils::isNotEmpty).collect(Collectors.toList());

        //如果已括号公式起头，则接在data后面
        if(!paths.isEmpty()){
            if(paths.get(0).startsWith("(")){
                String dataKey = stack.pop()+paths.get(0);
                stack.add(dataKey);
                paths.remove(0);
            }
        }
        stack.addAll(paths);
        Collections.reverse(stack);
        fetchRows(stack, respMap, targetRows);
        //开始处理
        if (targetRows.size() > 0) {
            //提取key
            List<Pair<Object, Map>> pairList = new ArrayList<>();
            Set<Object> keyValues= new HashSet<>();
            for (Map row : targetRows) {
                Object keyValue = getKeyValue(row, keyField);
                if(keyValue==null){
                    continue;
                }
                Pair pair = Pair.of(keyValue, row);
                pairList.add(pair);
                keyValues.add(keyValue.toString());
            }
            render.preRender(keyValues);
            //渲染查询
            for (Pair<Object, Map> pair : pairList) {
                Map<String, Object> rowFrame = render.getRenderFrame(pair.getLeft(), pair.getRight());
                rowDataRender(pair.getRight(), rowFrame, keyField,defaultAlias,wapperType);
            }
        }
    }

    private static Object getKeyValue(Map row, String keyField) {
        Object obj = row.get(keyField);
        return obj;
    }


    private static void rowDataRender(Map row, Map<String, Object> renderFrame, String keyField,String defaultAlias,String wapperType) {
        if (renderFrame != null) {
            if (Objects.equals(wapperType, DataRenderProvider.WapperType.alias.name())) {
                String alias = defaultAlias;
                if (StringUtils.isEmpty(alias)) {
                    alias = "_" + keyField;
                }
                row.put(alias, renderFrame);
            } else if (Objects.equals(wapperType, DataRenderProvider.WapperType.flat.name())) {
                for (String key : renderFrame.keySet()) {
                    row.put(key, renderFrame.get(key));
                }
            }
        }
    }


    /**
     * 找到目标行，目标行必须是对象或map，找到目标行后默认都转成map，保留属性的原始类型
     *
     * @param paths
     * @param targetObj
     * @param targetRows
     */
    private static void fetchRows(Stack<String> paths, Map targetObj, List<Map> targetRows) {
        String key = paths.pop();
        String realKey = key;
        boolean end = paths.size() == 0;
        boolean isLoop = false;
        String loopChildKey = null;
        if(end){
            Pair<String,String> keyArg = getKeyPath(key);
            if(StringUtils.isNotEmpty(keyArg.getRight())){
                isLoop = true;
                realKey = keyArg.getLeft();
                loopChildKey = keyArg.getRight();
            }
        }
        Object obj = targetObj.get(realKey);
        if (obj == null) {
            return;
        }
        if (obj.getClass().isArray()) {
            //FIXME
        } else if (obj instanceof Collection) {
            Iterator it = ((Collection) obj).iterator();
            List list = new ArrayList();
            while (it.hasNext()) {
                Map rowMap = BeanUtil.beanToMap(it.next());
                list.add(rowMap);
                if(end){
                    targetRows.add(rowMap);
                    if(isLoop){
                        getLoopRowsMap(loopChildKey,rowMap,targetRows);
                    }
                }else{
                    fetchRows(paths, rowMap, targetRows);
                }
            }
            //替换原对象
            targetObj.put(realKey,list);
        } else {
            Map rowMap = BeanUtil.beanToMap(obj);
            if (end) {
                targetRows.add(rowMap);
                if(isLoop) {
                    getLoopRowsMap(loopChildKey,rowMap,targetRows);
                }
            } else {
                fetchRows(paths, rowMap, targetRows);
            }
            //替换原对象
            targetObj.put(realKey,rowMap);
        }
        paths.add(key);
    }


    /**
     * 获取层级嵌套的，逻辑结构与上面基本一致
     * @param loopKey
     * @param targetObj
     * @param targetRows
     */
    private static void getLoopRowsMap(String loopKey,Map targetObj,List<Map> targetRows){
        Object obj = targetObj.get(loopKey);
        if (obj == null) {
            return;
        }
        if (obj.getClass().isArray()) {
            //FIXME
        } else if (obj instanceof Collection) {
            Iterator it = ((Collection) obj).iterator();
            List list = new ArrayList();
            while (it.hasNext()) {
                Map rowMap = BeanUtil.beanToMap(it.next());
                list.add(rowMap);
                targetRows.add(rowMap);
                boolean end = !rowMap.containsKey(loopKey);
                if(!end){
                    getLoopRowsMap(loopKey, rowMap, targetRows);
                }
            }
            //替换原对象
            targetObj.put(loopKey,list);
        }else{
            Map rowMap = BeanUtil.beanToMap(obj);
            targetRows.add(rowMap);
            boolean end = !rowMap.containsKey(loopKey);
            if (!end) {
                getLoopRowsMap(loopKey, rowMap, targetRows);
            }
            //替换原对象
            targetObj.put(loopKey,rowMap);
        }

    }

    private static Pattern loopKeyPattern = Pattern.compile("^(?:(\\w+)\\s*)?\\(loop->(\\w+)\\)$");
    private static Pair<String,String> getKeyPath(String keyPath){
        Matcher matcher = loopKeyPattern.matcher(keyPath);
        if (matcher.find()) {
            return Pair.of(matcher.group(1),matcher.group(2));
        }else{
            return Pair.of(keyPath,null);
        }
    }
}
