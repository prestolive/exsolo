package cn.exsolo.console.pub.service;

import cn.exsolo.batis.core.BaseDAO;
import cn.exsolo.console.pub.vo.CommItemVO;
import cn.exsolo.kit.utils.ExAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author prestolive
 * @date 2021/10/8
 **/
@Service
public class ItemQueryService {

    @Autowired
    private BaseDAO baseDAO;

    public List<CommItemVO> getSelectList(String tag){
        ExAssert.isNull(tag);
        String sql = "select name as label,code as value from s_item where tag = #{tag} order by id";
        Map<String,Object> params =new HashMap<>();
        params.put("tag",tag);
        return baseDAO.queryForList(sql,params,CommItemVO.class);
    }
}
