package cn.exsolo.kit.setting;

import cn.exsolo.batis.core.BaseDAO;
import cn.exsolo.batis.core.Condition;
import cn.exsolo.kit.setting.po.ExSettingPropPO;
import cn.exsolo.kit.setting.utils.SettingClzHelper;
import cn.exsolo.kit.setting.vo.ExSettingInstanceVO;
import cn.exsolo.kit.setting.vo.ExSettingProviderVO;
import cn.exsolo.kit.utils.ExAssert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author prestolive
 * @date 2021/10/4
 **/
@Service
public class ExSettingService {

    @Autowired
    private BaseDAO baseDAO;

    private Map<String, ExSettingInstanceVO> instances = new ConcurrentHashMap<>();

    private String getKey(String clzName, String fieldName) {
        return String.format("%s:%s", clzName, fieldName);
    }

    public void init(ExSettingInstanceVO prop) {
        String key = getKey(prop.getClzName(), prop.getFieldName());
        instances.put(key, prop);
    }

    /**
     * 设置参数
     *
     * @param clzName
     * @param fieldName
     * @param propValue
     */
    public void setValue(String clzName, String fieldName, String propValue) {
        ExAssert.isNull(clzName,fieldName,propValue);
        //优先设置到内存
        setValue2Memory(clzName, fieldName, propValue);
        //然后再持久化
        ExSettingPropPO po = getSettingProp(clzName, fieldName);
        po.setPropValue(propValue);
        baseDAO.insertOrUpdateValueObject(po);
    }

    /**
     * 设置到内存
     *
     * @param clzName
     * @param fieldName
     * @param propValue
     */
    public void setValue2Memory(String clzName, String fieldName, String propValue) {
        //设置静态类
        SettingClzHelper.setFieldValue(
                clzName,
                fieldName,
                propValue);
        //设置缓存
        String key = getKey(clzName, fieldName);
        ExSettingInstanceVO instance = getPropInstanceByKey(key);
        if (instance != null) {
            instance.setPropValue(propValue);
        }
    }

    /**
     * 返回所有数据库中的配置
     *
     * @return
     */
    public List<ExSettingPropPO> getAllSettingPropFromDB() {
        List<ExSettingPropPO> list = baseDAO.queryBeanByCond(ExSettingPropPO.class, new Condition());
        return list;
    }

    private ExSettingInstanceVO getPropInstanceByKey(String key) {
        return instances.get(key);
    }

    /**
     * 获取数据库配置，没有就新建一个
     *
     * @param clzName
     * @param fieldName
     * @return
     */
    private ExSettingPropPO getSettingProp(String clzName, String fieldName) {
        String key = getKey(clzName, fieldName);
        ExSettingInstanceVO instance = getPropInstanceByKey(key);
        ExSettingPropPO dbProp = baseDAO.queryOneBeanByCond(ExSettingPropPO.class,
                new Condition().eq("clzName", clzName).eq("fieldName", fieldName));
        if (dbProp == null) {
            dbProp = new ExSettingPropPO();
            BeanUtils.copyProperties(instance, dbProp);
        }
        return dbProp;
    }

    /**
     * 获取全部模块
     * @return
     */
    public List<ExSettingProviderVO> getAllSettingProviders() {
        Set<String> exists = new HashSet<>();
        List<ExSettingProviderVO> list = new ArrayList<>();
        for (ExSettingInstanceVO instance : instances.values()) {
            String clzName = instance.getClzName();
            if (!exists.contains(clzName)) {
                ExSettingProviderVO provider = new ExSettingProviderVO();
                provider.setModuleClz(clzName);
                provider.setModuleName(instance.getModuleName());
                String modulePackage = Arrays.stream(clzName.split("\\.")).limit(3).collect(Collectors.joining("."));
                provider.setModulePackage(modulePackage);
                exists.add(provider.getModuleClz());
                list.add(provider);
            }
        }
        list.sort(new Comparator<ExSettingProviderVO>() {
            @Override
            public int compare(ExSettingProviderVO o1, ExSettingProviderVO o2) {
                String sysPackage = "cn.exsolo";
                if (o1.getModulePackage().startsWith(sysPackage) && o2.getModulePackage().startsWith(sysPackage)) {
                    return o1.getModulePackage().compareTo(o2.getModulePackage());
                } else if (o1.getModulePackage().startsWith(sysPackage)) {
                    return 1;
                } else if (o2.getModulePackage().startsWith(sysPackage)) {
                    return -1;
                }
                return o1.getModulePackage().compareTo(o2.getModulePackage());
            }
        });
        return list;
    }

    public List<ExSettingInstanceVO> getSettingPropsByClz(String clzName){
        ExAssert.isNull(clzName);
        List<ExSettingInstanceVO> list=  instances.values().stream().filter(row->row.getClzName().equals(clzName)).collect(Collectors.toList());
        list.sort(new Comparator<ExSettingInstanceVO>() {
            @Override
            public int compare(ExSettingInstanceVO o1, ExSettingInstanceVO o2) {
                return o1.getSortNo()-o2.getSortNo();
            }
        });
        return list;
    }


}
