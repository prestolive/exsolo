package cn.exsolo.kit.setting;

import cn.exsolo.comm.utils.ExAnnotationUtil;
import cn.exsolo.kit.setting.stereotype.SettingProp;
import cn.exsolo.kit.setting.stereotype.SettingProvider;
import cn.exsolo.kit.setting.po.ExSettingPropPO;
import cn.exsolo.kit.setting.utils.SettingClzHelper;
import cn.exsolo.kit.setting.vo.ExSettingInstanceVO;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;

/**
 * FIXME 执行顺序
 * @author prestolive
 * @date 2023/10/4
 **/
@Component
public class ExSettingBootAware implements ApplicationContextAware {

    @Autowired
    private ExSettingService exSettingService;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //从注解构建
        List<Class<?>> list = ExAnnotationUtil.getAnnotationFromContext(applicationContext, SettingProvider.class);
        for (Class clz : list) {
            SettingProvider settingProvider = (SettingProvider) clz.getAnnotation(SettingProvider.class);
            String moduleName = settingProvider.value();
            //第一遍初始化 //FIXME 执行顺序导致的脏读问题
            SettingClzHelper.initClzField(clz);
            //提取到缓存中
            Field[] fields = clz.getDeclaredFields();
            if (fields != null) {
                int i=0;
                for (Field field : fields) {
                    SettingProp settingProp = field.getAnnotation(SettingProp.class);
                    if (settingProp == null) {
                        continue;
                    }
                    ExSettingInstanceVO vo = new ExSettingInstanceVO();
                    vo.setModuleName(moduleName);
                    vo.setClzName(clz.getName());
                    vo.setFieldName(field.getName());
                    vo.setGroupName(settingProp.group());
                    vo.setPropName(settingProp.label());
                    vo.setPropValue(settingProp.defaultValue());
                    vo.setInputType(settingProp.inputType());
                    //界面属性，不做持久化的部分
                    vo.setPickerCode(settingProp.pickerCode());
                    vo.setSortNo(i++);
                    vo.setDesc(settingProp.desc());
                    vo.setPrefix(settingProp.prefix());
                    vo.setSuffix(settingProp.suffix());
                    vo.setRequire(settingProp.isRequire());
                    vo.setProtect(settingProp.isProtect());
                    vo.setRequireInInit(settingProp.isRequireInInit());
                    //
                    exSettingService.init(vo);
                }
            }
        }
        //从数据库加载配置
        List<ExSettingPropPO> dbProps = exSettingService.getAllSettingPropFromDB();
        if(dbProps!=null){
            for(ExSettingPropPO prop:dbProps){
                exSettingService.setValue2Memory(prop.getClzName(),prop.getFieldName(),prop.getPropValue());
            }
        }
    }
}
