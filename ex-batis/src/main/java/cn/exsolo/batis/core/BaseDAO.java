package cn.exsolo.batis.core;

import cn.exsolo.batis.core.ex.BaseOrmException;
import cn.exsolo.batis.core.ext.ExecuteAdapter;
import cn.exsolo.batis.core.utils.GenerateID;
import cn.exsolo.comm.ex.ExDevException;
import com.google.common.reflect.TypeToken;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by prestolive on 2018/7/25.
 */
@Service
public class BaseDAO {

    @Autowired
    private ExecuteAdapter executeAdapter;

    /**
     * sql模板缓存
     */
    private final TinyCache<String,String> sqlCache;

    private static final Logger log = LoggerFactory.getLogger(BaseDAO.class.getName());

    public BaseDAO() {
        sqlCache = new TinyCache<>(1000);
    }

    /**
     * 通用的插入更新操作
     *
     * @param vo
     * @return
     * @throws BaseOrmException
     */
    public String insertOrUpdateValueObject(AbstractPO vo) throws BaseOrmException {
        if (vo.getState() == 0) {
            vo.setState(1);
        }
        switch (vo.getState()) {
            case 1: {
                if (vo.getId() == null || vo.getId().trim().isEmpty()) {
                    vo.setId(GenerateID.next());
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
                cond.eq("TS", vo.getTs());
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
    public <T extends AbstractPO> void insertOrUpdateValueObjectBatch(List<T> list) throws BaseOrmException {
        List<AbstractPO> inserts = new ArrayList<>();
        List<AbstractPO> updates = new ArrayList<>();
        for (AbstractPO vo : list) {
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
        for (AbstractPO vo : updates) {
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
        for (AbstractPO vo : inserts) {
            if (vo.getId() == null || vo.getId().trim().isEmpty()) {
                vo.setId(GenerateID.next());
            }
        }
        if (!inserts.isEmpty()) {
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
    public <T extends AbstractPO> int removeByID(Class clz, String id) throws BaseOrmException {
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
    public <T extends AbstractPO> int removeByCond(Class clz, Condition cond) throws BaseOrmException {
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
    public <T extends AbstractPO> int deleteByID(Class clz, String id) throws BaseOrmException {
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
    public <T extends AbstractPO> int deleteByCond(Class clz, Condition cond) throws BaseOrmException {
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

    public <T extends AbstractPO> T queryBeanByID2(TypeToken<T> type, String id) throws BaseOrmException {
        Class clz = type.getRawType();
        Condition cond = new Condition();
        cond.eq("ID", id);
        List<T> result = this.queryBeanByCond(clz, cond);
        if (result != null && result.size() > 0) {
            return result.get(0);
        }
        return null;
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
    public <T extends AbstractPO> T queryBeanByID(Class clz, String id) throws BaseOrmException {
        Condition cond = new Condition();
        cond.eq("ID", id);
        List<T> result = this.queryBeanByCond(clz, cond);
        if (result != null && !result.isEmpty()) {
            return result.get(0);
        }
        return null;
    }


    /**
     * 根据Condition 返回集合
     *
     * @param clz
     * @param cond
     * @param <T>
     * @return
     * @throws BaseOrmException
     */
    public <T extends AbstractPO> List<T> queryBeanByCond(Class clz, Condition cond) throws BaseOrmException {
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
     * 根据Condition 返回认知上唯一的数据
     *
     * @param clz
     * @param cond
     * @param <T>
     * @return
     * @throws BaseOrmException
     */
    public <T extends AbstractPO> T queryOneBeanByCond(Class clz, Condition cond) throws BaseOrmException {
        List<T> list = queryBeanByCond(clz, cond);
        if (list != null && list.size() > 1) {
            throw new BaseOrmException("预期返回条数不大于1，实际返回条数" + list.size());
        } else if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public <T extends AbstractPO> boolean existsByCond(Class clz, Condition cond) {
        List<T> list = queryBeanByCond(clz, cond);
        return list != null && !list.isEmpty();

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
    public <T extends AbstractPO> PageObject<T> queryBeanPageByCond(Class clz, Condition cond, Integer currIdx) throws BaseOrmException {
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
    public <T extends AbstractPO> PageObject<T> queryBeanPageByCond(Class clz, Condition cond, Pagination pagination) {
        String tableName = CommonOrmUtils.getTableFromClz(clz);
        List<String> fieldList = CommonOrmUtils.getTableColumnFromClz(clz);
        fieldList.add("createTs");
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

    public <T> List<T> queryForList(String sql, Map<String, Object> values, Class resultType) {
        return executeAdapter.executeQuery(sql, values, resultType);
    }

    public <T> List<T> queryForList(String originSql,Condition cond, Map<String, Object> values, Class resultType) {
        String sql = generateSqlWithCond(originSql,cond,values);
        return queryForList(sql,values,resultType);
    }

    public <T> T queryForOneObject(String sql, Map<String, Object> values, Class resultType) {
        List<T> list = executeAdapter.executeQuery(sql, values, resultType);
        if (list != null && list.size() > 1) {
            throw new BaseOrmException("预期返回条数不大于1，实际返回条数" + list.size());
        } else if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    public <T> T queryForOneObject(String originSql,Condition cond, Map<String, Object> values, Class resultType) {
        String sql = generateSqlWithCond(originSql,cond,values);
        return queryForOneObject(sql,values,resultType);
    }

    public <T> PageObject<T> queryForPage(String sql, Map<String, Object> values, Class resultType, Integer pageRows, Integer currIdx) {
        return executeAdapter.executeQueryPage(sql, values, resultType, pageRows, currIdx);
    }

    public <T> PageObject<T> queryForPage(String sql, Map<String, Object> values, Class resultType, Pagination pagination) {
        return executeAdapter.executeQueryPage(sql, values, resultType, pagination.getPageSize(), pagination.getCurrent());
    }
    public <T> PageObject<T> queryForPage(String originSql,Condition cond, Map<String, Object> values, Class resultType, Pagination pagination) {
        String sql = generateSqlWithCond(originSql,cond,values);
        return queryForPage(sql,values,resultType,pagination);
    }

    /**
     * 根据 cond 生成sql
     * @param sql
     * @param cond
     * @param values
     * @return
     */
    private String generateSqlWithCond(String sql,Condition cond,Map<String, Object> values){
        //cond转sql
        StringBuilder condSql = new StringBuilder();
        CommonOrmUtils.generateConditionSql(condSql,null,cond,values);
        String condSqlStr = condSql.toString();
        if(condSqlStr.toLowerCase().startsWith(" and")){
            condSqlStr = condSqlStr.substring(4);
        }
        StringBuilder orderSql = new StringBuilder();
        CommonOrmUtils.generateOrderSql(orderSql,null,cond);
        String orderSqlStr = orderSql.toString();
        if(orderSqlStr.toLowerCase().startsWith(" order by")){
            orderSqlStr = orderSqlStr.substring(9);
        }
        if(StringUtils.isEmpty(condSqlStr)&&StringUtils.isEmpty(orderSqlStr)){
            return sql;
        }
        //用jsqlparser定位然后占位符替换的方式，其中order有两个是考虑到已存在order接上和没有order的情况
        String wherePlaceKey = "___#xy_chang_wai_where_yan_sheng_pin_2025#___";
        String orderPlaceKey = "___#xy_chang_wai_order_yan_sheng_pin_2025#___";
        String orderFullPlaceKey = "___#xy_chang_wai_full_order_yan_sheng_pin_2025#___";
        //用基础sql为key，从缓存获取，不用每次都处理AST分析
        String cacheKey = StringUtils.joinWith(":",sql,condSql,orderSql);
        String sqlTemplate = sqlCache.get(cacheKey);
        if(sqlTemplate==null){
            try {
                Statement statement =  CCJSqlParserUtil.parse(sql);
                if(statement instanceof Select) {
                    Select select = (Select) statement;
                    PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
                    Expression where = plainSelect.getWhere();
                    if(where != null){
                        Expression newCondition = new AndExpression(where, new Column(wherePlaceKey));
                        plainSelect.setWhere(newCondition);
                    }else{
                        EqualsTo equalsTo = new EqualsTo();
                        equalsTo.setLeftExpression(new Column("1"));
                        equalsTo.setRightExpression(new LongValue(1));
                        Expression newCondition = new AndExpression(equalsTo, new Column(wherePlaceKey));
                        plainSelect.setWhere(newCondition);
                    }
                    List<OrderByElement> orderByList = plainSelect.getOrderByElements();
                    if (orderByList == null) {
                        orderByList = new ArrayList<>();
                    }
                    if(StringUtils.isNotEmpty(orderSqlStr)){
                        OrderByElement newOrder = new OrderByElement();
                        newOrder.setExpression(CCJSqlParserUtil.parseExpression(orderPlaceKey));
                        newOrder.setAsc(true);
                        orderByList.add(newOrder);
                    }
                    plainSelect.setOrderByElements(orderByList);
                    //最终生成
                    sqlTemplate= select.toString();
                    if(orderByList.isEmpty()){
                        sqlTemplate +=" ";
                        sqlTemplate +=orderFullPlaceKey;
                    }
                }else{
                    throw new ExDevException("非Select语句暂不支持");
                }
            } catch (JSQLParserException e) {
                throw new ExDevException(e.getMessage(),e);
            }
            sqlCache.put(cacheKey,sqlTemplate);
        }
        //替换
        sqlTemplate = sqlTemplate.replace(wherePlaceKey,StringUtils.isEmpty(condSqlStr)?"1=1":condSqlStr);
        if(StringUtils.isNotEmpty(orderSqlStr)){
            sqlTemplate = sqlTemplate.replace(orderPlaceKey,orderSqlStr);
            sqlTemplate = sqlTemplate.replace(orderFullPlaceKey," order by "+orderSqlStr);
        }else{
            sqlTemplate = sqlTemplate.replace(orderFullPlaceKey,"");
        }
        return sqlTemplate;
    }

}
