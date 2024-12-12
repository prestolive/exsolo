package cn.exsolo.basic.picker;

import cn.exsolo.basic.tree.CommonTreeNodePO;
import cn.exsolo.batis.core.BaseDAO;
import cn.exsolo.batis.core.Condition;
import cn.exsolo.batis.core.PageObject;
import cn.exsolo.batis.core.Pagination;
import cn.exsolo.kit.picker.ITreePicker;
import cn.exsolo.kit.picker.bo.ExPickerOptionBO;
import cn.exsolo.kit.render.DataRender;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * @author prestolive
 * @date 2024/11/14
 **/
@Component
public abstract  class AbstractTreeNodePicker<T extends CommonTreeNodePO> implements ITreePicker {

    @Autowired
    private BaseDAO baseDAO;

    protected abstract ExPickerOptionBO value2option(T origin);

    protected abstract PageObject<T> query(Pagination pagination, String keyword, Condition fCond);

    @Override
    public PageObject<ExPickerOptionBO> find(Pagination pagination, String keyword, Condition fCond) {
        PageObject<T> page =query(pagination,keyword,fCond);
        List<ExPickerOptionBO> optionBOS = new ArrayList<>();
        if(page.getValues()!=null){
            for(T t:page.getValues()){
                optionBOS.add(value2option(t));
            }
        }
        PageObject<ExPickerOptionBO> resultPage= new PageObject<>();
        resultPage.setValues(optionBOS);
        resultPage.setPagination(page.getPagination());
        return resultPage;
    }

    @Override
    public List<ExPickerOptionBO> getList(List<String> ids) {
        Condition cond = new Condition();
        cond.in("id",ids);
        Pagination pagination = new Pagination(1, ids.size());
        PageObject<T> page = query(pagination,null,cond);
        List<ExPickerOptionBO> optionBOS = new ArrayList<>();
        if(page.getValues()!=null){
            for(T t:page.getValues()){
                optionBOS.add(value2option(t));
            }
        }
        return optionBOS;
    }

    @Override
    public List<ExPickerOptionBO> getNodes(String parentId) {
        Condition cond = new Condition();
        if(StringUtils.isNotEmpty(parentId)){
            cond.eq("parentId",parentId);
        }else{
            cond.isEmpty("parentId");
        }
        Pagination pagination = new Pagination(1,999);
        PageObject<T> page = query(pagination,null,cond);
        List<ExPickerOptionBO> optionBOS = new ArrayList<>();
        if(page.getValues()!=null){
            for(T t:page.getValues()){
                optionBOS.add(value2option(t));
            }
        }
        return optionBOS;
    }
}
