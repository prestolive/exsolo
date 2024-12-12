package cn.exsolo.kit.picker;

import cn.exsolo.kit.picker.bo.ExPickerOptionBO;

import java.util.List;

/**
 * 树形数据picker
 * @author prestolive
 * @date 2024/11/14
 **/
public interface ITreePicker extends IPicker{

    List<ExPickerOptionBO> getNodes(String parentId);
}
