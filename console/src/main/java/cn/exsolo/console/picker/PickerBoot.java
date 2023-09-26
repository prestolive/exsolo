package cn.exsolo.console.picker;

import cn.exsolo.comm.utils.ExAnnotationUtil;
import cn.exsolo.kit.picker.IPicker;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author prestolive
 * @date 2021/8/20
 **/

@Component
public class PickerBoot implements ApplicationContextAware {

    @Autowired
    private PickerFactory pickerFactory;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        List<Class<?>> list = ExAnnotationUtil.getAllClassByInterface(applicationContext, IPicker.class);
        Map<String,IPicker> map = new HashMap<>();
        for(Class clz:list){
            IPicker instance = (IPicker) applicationContext.getBean(clz);
            map.put(instance.getCode(),instance);
        }
        pickerFactory.setInstances(map);
    }
}
