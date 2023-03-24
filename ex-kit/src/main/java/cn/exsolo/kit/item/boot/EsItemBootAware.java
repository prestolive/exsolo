package cn.exsolo.kit.item.boot;

import cn.exsolo.kit.item.po.ItemTagPO;
import cn.exsolo.kit.item.stereotype.Item;
import cn.exsolo.kit.item.ItemStatusEnum;
import cn.hutool.core.util.EnumUtil;
import cn.exsolo.batis.core.BaseDAO;
import cn.exsolo.batis.core.Condition;
import cn.exsolo.kit.ex.EsBuilderException;
import cn.exsolo.kit.item.ItemOriginEnum;
import cn.exsolo.kit.item.ItemSchemaEnum;
import cn.exsolo.kit.item.po.ItemPO;
import cn.exsolo.kit.item.po.ItemTextPO;
import cn.exsolo.comm.utils.EsAnnotationUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 对象管理初始化程序
 * 每次启动会检查初始化情况，执行的内容有：
 * 0、检查枚举表是否存在，无则添加，反之更新
 * 1、扫描所有的枚举类，检查数据库是否存在，无则添加，反之更新
 * 2、FIXME  扫描所有参照注解，对比数据库参照表，无则添加，反之更新
 * 3、本次启动不做，但是任务会自动执行的：自动对比用户自定义表和参照注解，修复未同步的数据。
 * FIXME 微服务或集群状态下，不同版本的代码如何避免冲突，建议写入数据库
 *
 * @author prestolive
 */
@Transactional(rollbackOn = Exception.class)
@Component
public class EsItemBootAware implements ApplicationContextAware {

    @Autowired
    private BaseDAO baseDAO;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //从注解构建
        List<Class<?>> list = EsAnnotationUtil.getAnnotationFromContext(applicationContext, Item.class);
        List<ItemTagPO> allItems = list.stream().map(clz -> {
            Item itemAnna = clz.getAnnotation(Item.class);
            ItemTagPO po = new ItemTagPO();
            po.setName(itemAnna.name());
            po.setId(itemAnna.tag());
            po.setSchema(itemAnna.schema());
            po.setClz(clz.getName());
            po.setModule(getModuleFromClz(po.getClz()));

            return po;
        }).collect(Collectors.toList());
        initTag(allItems);
        initTagEnumValues(allItems);
    }

    /**
     * 同步对象类型
     *
     * @param allItems
     */
    private void initTag(List<ItemTagPO> allItems) {
        List<ItemTagPO> exists = baseDAO.queryBeanByCond(ItemTagPO.class, new Condition());
        for (ItemTagPO item : allItems) {
            ItemTagPO exist = exists.stream().filter(row -> row.getId().equals(item.getId())).findFirst().orElse(null);
            if (exist == null) {
                baseDAO.insertOrUpdateValueObject(item);
            } else {
                if (ObjectUtils.notEqual(item.getName(), exist.getName()) ||
                        ObjectUtils.notEqual(item.getSchema(), exist.getSchema())||
                        ObjectUtils.notEqual(item.getClz(), exist.getClz())||
                        ObjectUtils.notEqual(item.getModule(), exist.getModule())
                ) {
                    exist.setName(item.getName());
                    exist.setSchema(item.getSchema());
                    exist.setClz(item.getClz());
                    exist.setModule(item.getModule());
                    exist.setCustomAble(item.getCustomAble());
                    baseDAO.insertOrUpdateValueObject(exist);
                }
            }
        }
    }

    private void initTagEnumValues(List<ItemTagPO> allItems) {
        try {
            List<ItemPO> exists = baseDAO.queryBeanByCond(ItemPO.class,new Condition().eq("origin",ItemOriginEnum.SOURCE.name()));
            for (ItemTagPO item : allItems) {
                Class clz = null;
                clz = Class.forName(item.getClz());
                Map<String, Object> map =  EnumUtil.getNameFieldMap(clz, "name");
                for(Map.Entry<String,Object> entry:map.entrySet()){
                    ItemPO po = new ItemPO();
                    po.setTag(item.getId());
                    po.setCode(entry.getKey());
                    po.setName((String)entry.getValue());
                    po.setOrigin(ItemOriginEnum.SOURCE);
                    po.setSchema(ItemSchemaEnum.ENUM);
                    po.setStatus(ItemStatusEnum.NORMAL);
                    po.setText(false);
                    ItemPO exist = exists.stream().filter(row->(!ObjectUtils.notEqual(row.getTag(),po.getTag())&&!ObjectUtils.notEqual(row.getCode(),po.getCode()))).findFirst().orElse(null);
                    if(exist==null){
                        saveItem(po);
                    }else{
                        exist.setName(po.getName());
                        saveItem(exist);
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            throw new EsBuilderException(e.getMessage(),e);
        }
    }

    private void saveItem(ItemPO po){
        if(po.getName().length()>128){
            String fullName = po.getName();
            po.setName(fullName.substring(0,21)+"...");
            baseDAO.removeByID(ItemTextPO.class,po.getId());
            ItemTextPO textPO = new ItemTextPO();
            textPO.setId(po.getId());
            textPO.setContent(fullName);
            baseDAO.insertOrUpdateValueObject(textPO);
        }else{
            baseDAO.insertOrUpdateValueObject(po);
        }

    }

    private String getModuleFromClz(String clzName){
        String[] arr = clzName.split("\\.");
        List<String> list = new ArrayList<>();
        for(int i=0;i<3;i++){
            if(arr.length>=(i+1)){
                list.add(arr[i]);
            }
        }
        return StringUtils.join(list,".");
    }
}