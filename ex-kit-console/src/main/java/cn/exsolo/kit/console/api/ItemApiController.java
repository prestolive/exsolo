package cn.exsolo.kit.console.api;

import cn.exsolo.batis.core.*;
import cn.exsolo.kit.item.po.ItemPO;
import cn.exsolo.kit.item.po.ItemTagPO;
import cn.exsolo.springmvcext.stereotype.RequestJSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author prestolive
 * @date 2023/3/1
 **/
@Component
@RequestMapping("api/ex-kit-console/")
@RestController()
public class ItemApiController {

    @Autowired
    private BaseDAO baseDAO;

    @RequestMapping("item-tags")
    public List<ItemTagPO> tags() {
        return baseDAO.queryBeanByCond(ItemTagPO.class, new Condition().orderBy("id"));
    }

    @RequestMapping(path = "items", method = RequestMethod.POST)
    public PageObject<ItemPO> items(@RequestParam(required = false) String module,
                                    @RequestParam(required = false) String schema,
                                    @RequestParam(required = false) String tag,
                                    @RequestParam(required = false) String keyword,
                                    @RequestJSON Pagination pagination) {
        Condition cond = new Condition();
        if(StringUtils.isNotEmpty(module)){
            ConditionFilter filter = new ConditionFilter("tag",ItemTagPO.class,"id");
            filter.eq("module",module);
            cond.exist(filter);
        }
        if(StringUtils.isNotEmpty(schema)){
            cond.eq("schema",schema);
        }
        if(StringUtils.isNotEmpty(tag)){
            cond.eq("tag",tag);
        }
        if(StringUtils.isNotEmpty(keyword)){
            cond.or(new Condition().lk("code",keyword),
                    new Condition().lk("name",keyword));
        }
        cond.orderBy("id");
        return baseDAO.queryBeanPageByCond(ItemPO.class,cond,pagination);
    }
}
