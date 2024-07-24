package cn.exsolo.kit.dev;

import cn.exsolo.batis.core.BaseDAO;
import cn.exsolo.comm.utils.ExAnnotationUtil;
import cn.exsolo.kit.dev.bo.DevClzBO;
import cn.exsolo.kit.dev.bo.ModelFieldBO;
import cn.exsolo.kit.dev.bo.ModelMetaBO;
import cn.exsolo.kit.dev.po.ModelMetaDevPO;
import cn.exsolo.kit.item.stereotype.ItemProvider;
import cn.hutool.core.util.ReflectUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 不想写英文了，这个类用来扫@Table注解，根据模型生成代码
 * @author prestolive
 * @date 2021/6/26
 **/

@Service
public class ModelDocService {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private BaseDAO baseDAO;

    private static DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();

    public List<DevClzBO> getModels() {
        List<Class<?>> list = ExAnnotationUtil.getAnnotationFromContext(applicationContext, Table.class);
        return list.stream().map(row -> {
            DevClzBO bo = new DevClzBO();
            bo.setModule(getModuleFromClz(row.getName()));
            bo.setClz(row.getName());
            return bo;
        }).collect(Collectors.toList());
    }

    public void saveModelMetaDev(ModelMetaBO meta){
        String id = meta.getClzName();
        ModelMetaDevPO config = baseDAO.queryBeanByID(ModelMetaDevPO.class,id);
        if(config==null){
            config = new ModelMetaDevPO();
            config.setId(id);
        }else{
            config.setState(2);
        }
        config.setContent(JSONObject.toJSONString(meta));
        baseDAO.insertOrUpdateValueObject(config);
    }

    public ModelMetaBO processClz(Class clz) {
        String id = clz.getName();
        ModelMetaDevPO config = baseDAO.queryBeanByID(ModelMetaDevPO.class,id);
        ModelMetaBO meta = null;
        if(config!=null){
            try{
                meta = JSONObject.parseObject(config.getContent(), ModelMetaBO.class);
            }catch (Throwable e){
                e.printStackTrace();
            }
        }
        //提取最新的字段
        List<ModelFieldBO> list = new ArrayList<>();
        Table clzAnna = (Table) clz.getAnnotation(Table.class);
        Field[] fields = ReflectUtil.getFields(clz);
        for (Field field : fields) {
            Column col = field.getAnnotation(Column.class);
            if (col == null) {
                continue;
            }
            ModelFieldBO row = new ModelFieldBO();
            row.setCode(col.name());
            row.setName(col.name()+"释义");
            row.setDbType(col.columnDefinition());
            row.setJavaType(field.getType().getName());
            row.setInTable(true);
            row.setInForm(true);
            row.setInCondition(true);
            row.setEditAble(true);
            row.setRequired(!col.nullable());
            String jsType = JsTypeMapEnum.getJavaScriptTypeName(field.getType());
            row.setJsType(jsType);
            //compareType
            if("string".equals(row.getJsType())){
                if(col.columnDefinition().contains("varchar")){
                    row.setCompareType("lk");
                }
            }
            ItemProvider itemProvider = field.getType().getAnnotation(ItemProvider.class);
            if(itemProvider!=null){
                row.setInputArg(itemProvider.tag());
                row.setInputType("selector");
                row.setCompareType("eq");
            }
            list.add(row);
        }
        if(meta==null){
            meta = new ModelMetaBO();
            meta.setDbTableName(clzAnna.name());
            meta.setClzName(clz.getName());
            meta.setPackageName(getPackageName(clz.getName()));
        }else{
            //如果meta已存在则将配置迁移出来
            for(ModelFieldBO field:list){
                ModelFieldBO exist = meta.getFields().stream().filter(row->row.getCode().equals(field.getCode())).findFirst().orElse(null);
                if(exist==null){
                    continue;
                }
                field.setName(exist.getName());
                field.setInTable(exist.getInTable());
                field.setInCondition(exist.getInCondition());
                field.setInForm(exist.getInForm());
                field.setEditAble(exist.getEditAble());
                field.setRequired(exist.getRequired());
            }
        }
        meta.setFields(list);
        return meta;
    }

    private String getModuleFromClz(String clzName) {
        String[] arr = clzName.split("\\.");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            if (arr.length >= (i + 1)) {
                list.add(arr[i]);
            }
        }
        return StringUtils.join(list, ".");
    }

    private String getPackageName(String clzName) {
        String[] arr = clzName.split("\\.");
        if(arr.length<2){
            return "";
        }
        List<String> list = new ArrayList<>();
        for (int i = 0; i < arr.length - 2; i++) {
            list.add(arr[i]);
        }
        return StringUtils.join(list, ".");
    }

}
