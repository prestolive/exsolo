package cn.exsolo.console.picker;

import cn.exsolo.kit.picker.IPicker;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author prestolive
 * @date 2021/8/20
 **/
@Service
public class PickerFactory {

    private Map<String, IPicker> instances = new HashMap<>();

    public void setInstances(Map<String, IPicker> instances) {
        this.instances = instances;
    }

    public IPicker getPicker(String code){
        return instances.get(code);
    }
}
