package cn.exsolo.kit.console.api;

import cn.exsolo.auth.shiro.ext.stereotype.AccessConfig;
import cn.exsolo.auth.shiro.ext.stereotype.AccessEdit;
import cn.exsolo.auth.shiro.ext.stereotype.AccessProvider;
import cn.exsolo.auth.shiro.ext.stereotype.AccessView;
import cn.exsolo.batis.core.*;
import cn.exsolo.comm.ex.ExBizException;
import cn.exsolo.kit.item.ItemCommStatusEnum;
import cn.exsolo.kit.item.po.ItemPO;
import cn.exsolo.kit.item.po.ItemTagPO;
import cn.exsolo.kit.utils.ExAssert;
import cn.exsolo.springmvcext.stereotype.RequestJSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Access;
import java.util.List;

/**
 * @author prestolive
 * @date 2021/3/1
 **/
@Component
@RequestMapping("api/ex-kit-console/")
@RestController()
@AccessProvider(module = "kit", node = "item", label = "开发套件-对象管理")
public class ItemApiController {

    @Autowired
    private BaseDAO baseDAO;

    @AccessView
    @RequestMapping(path = "item-tags", method = RequestMethod.POST)
    public List<ItemTagPO> tags() {
        return baseDAO.queryBeanByCond(ItemTagPO.class, new Condition().orderBy("id"));
    }

    @AccessView
    @RequestMapping(path = "get-tag", method = RequestMethod.POST)
    public ItemTagPO getItemTag( @RequestJSON String id) {
        return baseDAO.queryBeanByID(ItemTagPO.class,id);
    }

    @AccessView
    @RequestMapping(path = "items", method = RequestMethod.POST)
    public PageObject<ItemPO> items(@RequestParam(required = false) String module,
                                    @RequestParam(required = false) String itemType,
                                    @RequestParam(required = false) String tag,
                                    @RequestJSON Condition cond,
                                    @RequestJSON Pagination pagination) {
        Condition queryCond = new Condition();
        if (StringUtils.isNotEmpty(module)) {
            ConditionFilter filter = new ConditionFilter("tag", ItemTagPO.class, "id");
            filter.eq("module", module);
            queryCond.exist(filter);
        }
        if (StringUtils.isNotEmpty(itemType)) {
            queryCond.eq("itemType", itemType);
        }
        if (StringUtils.isNotEmpty(tag)) {
            queryCond.eq("tag", tag);
        }
        if (cond != null) {
            queryCond.and(cond);
        }
        queryCond.orderBy("id");
        return baseDAO.queryBeanPageByCond(ItemPO.class, queryCond, pagination);
    }

    @AccessEdit
    @RequestMapping(path = "create-item", method = RequestMethod.POST)
    public void createItem(@RequestJSON ItemPO item, @RequestJSON String tag) {
        ExAssert.isNull(tag);
        ItemTagPO itemTag = baseDAO.queryBeanByID(ItemTagPO.class, tag);
        ExAssert.isNull(itemTag);
        //判断同类型下没有重复
        if (baseDAO.existsByCond(ItemPO.class, new Condition().eq("code", item.getCode()))) {
            throw new ExBizException("编码重复");
        }
        //保存
        item.setSys(false);
        item.setTag(itemTag.getId());
        item.setItemType(itemTag.getItemType());
        item.setStatus(ItemCommStatusEnum.NORMAL);
        item.setText(false);
        baseDAO.insertOrUpdateValueObject(item);
    }

    @AccessEdit
    @RequestMapping(path = "delete-item", method = RequestMethod.POST)
    public void deleteItem(@RequestJSON String id) {
        ExAssert.isNull(id);
        baseDAO.deleteByID(ItemPO.class, id);
    }
}
