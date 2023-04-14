package cn.exsolo.kit.render.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author prestolive
 * @date 2023/3/14
 **/
public abstract class AbstractSqlDataRender extends BaseDataRender {

    private String alias;

    /**
     *
     * @param path
     * @param keyField
     * @param alias 别名，null或空则展开放在row上，可能存在覆盖值的风险。建议要传值，名称风格建议在原值前加下划线，例如：
     *              原值  userId ，别名就是 _userId，前端使用类似这样 _userId.name、_userId.birthday
     */
    public AbstractSqlDataRender(String path, String keyField, String alias) {
        super(path, keyField);
        this.alias = alias;
    }

    @Override
    protected void handleKeysBeforeRender(List<Map<String, Object>> rows, Set<String> keys) {

    }

    @Override
    protected void handleRowRender(Map<String, Object> row, String key) {

    }

    /**
     * 返回查询sql语句
     * 1、对于keys的部分，默认采用 in(#{keys})，格式类似如下，框架自动将#{keys}转成mybatis参数格式
     * select a.name.b.birthday from a inner join b on a.xx=b.xx where a.id in (#{keys})
     * 2、框架会解决超大keys集合问题，自动分成200一批酱紫。
     * @return
     */
    protected abstract String getSql();
}
