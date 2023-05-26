package cn.exsolo.batis.core;

import cn.exsolo.batis.core.condition.ICompareBean;
import cn.exsolo.batis.core.ex.BaseOrmException;
import cn.exsolo.batis.core.ext.ExecuteAdapter;
import cn.exsolo.batis.core.utils.GenerateID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by prestolive on 2018/7/25.
 */
@Service
public class BaseDAO {

    @Autowired
    private ExecuteAdapter executeAdapter;

    private static final Logger log = Logger.getLogger(BaseDAO.class.getName());
    /**
     * 通用的插入更新操作
     *
     * @param vo
     * @return
     * @throws BaseOrmException
     */
    public String insertOrUpdateValueObject(AbstractSanBatisPO vo) throws BaseOrmException {
        if (vo.getState() == 0) {
            vo.setState(1);
        }
        switch (vo.getState()) {
            case 1: {
                if (vo.getId() == null || vo.getId().trim().length() == 0) {
                    vo.setId(GenerateID.next());
                    try {
                        Field file = vo.getClass().getDeclaredField("id");
                        vo.setId(GenerateID.next());
                    } catch (NoSuchFieldException e) {
                        throw new BaseOrmException("ORM错误，试图获取ID的注解报错" + e.getMessage(), e);
                    }

                }
                StringBuilder sql = new StringBuilder();
                Map<String, Object> values = new HashMap<>();
                CommonOrmUtils.generateInsertSql(sql, values, vo);
                int updates = executeAdapter.executeUpdate(sql.toString(), values);
                if (updates > 0) {
                    vo.setState(2);
                }
                break;
            }
            case 2: {
                StringBuilder sql = new StringBuilder();
                Condition cond = new Condition();
                cond.eq("ID", vo.getId());
//                cond.eq("TS", vo.getTs());
                Map<String, Object> values = new HashMap<>();
                CommonOrmUtils.generateUpdateSql(sql, values, cond, vo);
                int updates = executeAdapter.executeUpdate(sql.toString(), values);
                if (updates != 1) {
                    throw new BaseOrmException("更新失败：可能是该数据已过期！");
                }
                break;
            }
            case 3: {
                removeByID(vo.getClass(), vo.getId());
            }
            default:{}
        }
        return vo.getId();
    }

    /**
     * 批量入更新操作
     *
     * @param list
     * @throws BaseOrmException
     */
    public <T extends AbstractSanBatisPO> void insertOrUpdateValueObjectBatch(List<T> list) throws BaseOrmException {
        List<AbstractSanBatisPO> inserts = new ArrayList<>();
        List<AbstractSanBatisPO> updates = new ArrayList<>();
        for (AbstractSanBatisPO vo : list) {
            if (vo.getState() == 0) {
                vo.setState(1);
            }
            if (vo.getState() == 1) {
                inserts.add(vo);
            }
            if (vo.getState() == 2) {
                updates.add(vo);
            }
        }
        for (AbstractSanBatisPO vo : updates) {
            StringBuilder sql = new StringBuilder();
            Condition cond = new Condition();
            cond.eq("ID", vo.getId());
            Map<String, Object> values = new HashMap<>();
            CommonOrmUtils.generateUpdateSql(sql, values, cond, vo);
            int num = executeAdapter.executeUpdate(sql.toString(), values);
            if (num != 1) {
                throw new BaseOrmException("更新失败：可能是该数据已过期！");
            }
        }
        for (AbstractSanBatisPO vo : inserts) {
            if (vo.getId() == null || vo.getId().trim().length() == 0) {
                vo.setId(GenerateID.next());
                try {
                    Field file = vo.getClass().getDeclaredField("id");
                    vo.setId(GenerateID.next());
                } catch (NoSuchFieldException e) {
                    throw new BaseOrmException("ORM错误，试图获取ID的注解报错" + e.getMessage(), e);
                }

            }
        }
        if (inserts.size() > 0) {
            StringBuilder head = new StringBuilder();
            StringBuilder body = new StringBuilder();
            List<Map<String, Object>> listParams = new ArrayList<>();
            CommonOrmUtils.generateInsertSqlBatch(head, body, listParams, inserts);
            executeAdapter.executeUpdateForBatch(head.toString(), body.toString(), listParams);
        }

    }

    /**
     * 根据ID删除
     *
     * @param clz
     * @param id
     * @param <T>
     * @return
     * @throws BaseOrmException
     */
    public <T extends AbstractSanBatisPO> int removeByID(Class<T> clz, String id) throws BaseOrmException {
        StringBuilder sql = new StringBuilder();
        Condition cond = new Condition();
        cond.eq("ID", id);
        Map<String, Object> values = new HashMap<>();
        CommonOrmUtils.generateRemoveSql(sql, values, cond, clz);
        int updates = executeAdapter.executeUpdate(sql.toString(), values);
        if (updates != 1) {
            throw new BaseOrmException("更新失败：可能是该数据已过期！");
        }
        return updates;
    }

    /**
     * 根据ID删除
     *
     * @param clz
     * @param cond
     * @param <T>
     * @return
     * @throws BaseOrmException
     */
    public <T extends AbstractSanBatisPO> int removeByCond(Class<T> clz, Condition cond) throws BaseOrmException {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> values = new HashMap<>();
        CommonOrmUtils.generateRemoveSql(sql, values, cond, clz);
        int updates = executeAdapter.executeUpdate(sql.toString(), values);
//        if (updates ==0) {
//            throw new BaseOrmException("更新失败：可能是该数据已过期！");
//        }
        return updates;
    }

    /**
     * 根据ID彻底删除
     *
     * @param clz
     * @param id
     * @param <T>
     * @throws BaseOrmException
     */
    public <T extends AbstractSanBatisPO> int deleteByID(Class<T> clz, String id) throws BaseOrmException {
        Condition cond = new Condition();
        cond.eq("ID", id);
        int updates = deleteByCond(clz, cond);
        if (updates != 1) {
            throw new BaseOrmException("更新失败：可能是该数据已过期！");
        }
        return updates;
    }


    /**
     * 功能：根据Condition条件,彻底删除(不推荐)
     *
     * @param clz
     * @param cond
     * @return
     */
    public <T extends AbstractSanBatisPO> int deleteByCond(Class<T> clz, Condition cond) throws BaseOrmException {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> values = new HashMap<>();
        CommonOrmUtils.generateDeleteSql(sql, values, cond, clz);
        int updates = executeAdapter.executeUpdate(sql.toString(), values);
        return updates;
    }

    public int updateByCustom(String sql, Map<String, Object> params) {
        int updates = executeAdapter.executeUpdate(sql.toString(), params);
        return updates;
    }

    /**
     * 根据ID 返回单个结果
     *
     * @param clz
     * @param id
     * @param <T>
     * @return
     * @throws BaseOrmException
     */
    public <T extends AbstractSanBatisPO> T queryBeanByID(Class<T> clz, String id) throws BaseOrmException {
        Condition cond = new Condition();
        cond.eq("ID", id);
        List<T> result = this.queryBeanByCond(clz, cond);
        if (result != null && result.size() > 0) {
            return result.get(0);
        }
        return null;
    }


    /**
     * 根据Condtion 返回集合
     *
     * @param clz
     * @param cond
     * @param <T>
     * @return
     * @throws BaseOrmException
     */
    public <T extends AbstractSanBatisPO> List<T> queryBeanByCond(Class<T> clz, Condition cond) throws BaseOrmException {
        String tableName = CommonOrmUtils.getTableFromClz(clz);
        List<String> fieldList = CommonOrmUtils.getTableColumnFromClz(clz);
        fieldList.add("createTs");
        //通用部分
        StringBuilder sql = new StringBuilder();
        Map<String, Object> values = new HashMap<>();
        CommonOrmUtils.generateQuerySql(sql, values, fieldList, tableName, "a", cond);
        List<T> list = executeAdapter.executeQuery(sql.toString(), values, clz);
        for (T t : list) {
            t.setState(2);
        }
        return list;
    }

    /**
     * 根据Condtion 返回认知上唯一的数据
     *
     * @param clz
     * @param cond
     * @param <T>
     * @return
     * @throws BaseOrmException
     */
    public <T extends AbstractSanBatisPO> T queryOneBeanByCond(Class<T> clz, Condition cond) throws BaseOrmException {
        List<T> list = queryBeanByCond(clz, cond);
        if (list != null && list.size() > 1) {
            List<ICompareBean> compires = cond.getCompares();
            for (ICompareBean compire : compires) {
                System.out.println(compire.toString());
            }
            throw new BaseOrmException("预期返回条数不大于1，实际返回条数" + list.size());
        } else if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    public <T extends AbstractSanBatisPO> boolean existsByCond(Class<T> clz, Condition cond) {
        List<T> list = queryBeanByCond(clz, cond);
        return list != null && list.size() > 0;

    }

    /**
     * 默认的分页查询
     *
     * @param clz
     * @param cond
     * @param currIdx
     * @param <T>
     * @return
     * @throws BaseOrmException
     */
    public <T extends AbstractSanBatisPO> PageObject<T> queryBeanPageByCond(Class<T> clz, Condition cond, Integer currIdx) throws BaseOrmException {
        Pagination pagination = new Pagination(currIdx, 10);
        return queryBeanPageByCond(clz, cond, pagination);
    }

    /**
     * 分页查询
     *
     * @param clz
     * @param cond
     * @param pagination
     * @param <T>
     * @return
     */
    public <T extends AbstractSanBatisPO> PageObject<T> queryBeanPageByCond(Class<T> clz, Condition cond, Pagination pagination) {
        String tableName = CommonOrmUtils.getTableFromClz(clz);
        List<String> fieldList = CommonOrmUtils.getTableColumnFromClz(clz);
        //通用部分
        StringBuilder sql = new StringBuilder();
        Map<String, Object> values = new HashMap<>();
        CommonOrmUtils.generateQuerySql(sql, values, fieldList, tableName, "a", cond);
        log.info(sql.toString());
        PageObject<T> page = executeAdapter.executeQueryPage(sql.toString(), values, clz, pagination.getPageSize(), pagination.getCurrent());
        for (T t : page.getValues()) {
            t.setState(2);
        }
        return page;
    }

    public <T> List<T> queryForList(String sql, Map<String, Object> values, Class<T> resultType) {
        return executeAdapter.executeQuery(sql, values, resultType);
    }

    public <T> T queryForOneObject(String sql, Map<String, Object> values, Class<T> resultType) {
        List<T> list = executeAdapter.executeQuery(sql, values, resultType);
        if (list != null && list.size() > 1) {
            throw new BaseOrmException("预期返回条数不大于1，实际返回条数" + list.size());
        } else if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    public <T> PageObject<T> queryForPage(String sql, Map<String, Object> values, Class<T> resultType, Integer pageRows, Integer currIdx) {
        return executeAdapter.executeQueryPage(sql, values, resultType, pageRows, currIdx);
    }

    public <T> PageObject<T> queryForPage(String sql, Map<String, Object> values, Class<T> resultType, Pagination pagination) {
        PageObject result = executeAdapter.executeQueryPage(sql, values, resultType, pagination.getPageSize(), pagination.getCurrent());
        return result;
    }

}
