package cn.exsolo.batis.core;

import cn.exsolo.batis.core.condition.*;
import cn.exsolo.batis.core.ex.BaseOrmException;
import cn.exsolo.batis.core.stereotype.Column;
import cn.exsolo.batis.core.stereotype.Table;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by prestolive on 2018/2/2.
 */
public class CommonOrmUtils {

    private static final SimpleDateFormat TIME_STAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static <T extends AbstractSanBatisPO> String getTableFromClz(Class<T> clz){
        Table table = (Table)clz.getAnnotation(Table.class);
        if (table != null){
            return table.value();
        }
        throw new BaseOrmException("该类没有实现Table注解"+clz.getName());
    }


    public static <T extends AbstractSanBatisPO> List<String> getTableColumnFromClz(Class<T> clz){
        String[] columns;
        /*临时代码*/
        List<String> columnList=new ArrayList<String>();
        Field[] fields  = clz.getDeclaredFields();
        for (Field f:fields) {
            Column col = f.getAnnotation(Column.class);
            if(col==null){
                continue;
            }
            String column=col.name();
            if("ts".equals(column)){
                continue;
            }
            columnList.add(column);
        }
        columnList.add("ts");
        return columnList;
    }

    /**
     * 通过给与的字段、表关联、Condition生成完整的sql结构
     * @param fields
     * @param table
     * @param tableAlias
     * @param cond
     * @return
     */
    public static void generateQuerySql(StringBuilder sql, Map<String,Object> values,List<String> fields, String table, String tableAlias, Condition cond) {

        sql.append(" select ");
        for (int i=0;i<fields.size();i++) {
            if(i>0){
                sql.append(",");
            }
            sql.append(tableAlias + "." + fields.get(i));
        }
        sql.append(" from ").append(table).append(" ").append(tableAlias);
        //where
        sql.append(" where 1=1 ");
        //将 Condition 转换成sql
        generateConditionSql(sql,tableAlias,cond,values);
        //固定条件
//        sql.append(" and coalesce("+tableAlias+".dr,0)=0");
        //order
        generateOrderSql(sql,tableAlias,cond);

    }

    public static void generateConditionSql(StringBuilder sql,String tableAlias, Condition cond, Map<String,Object> values){

        if (cond.getExistFilters() != null && cond.getExistFilters().size() > 0) {
            for (ConditionFilter filter : cond.getExistFilters()) {
                sql.append(" and exists ( select 1 from ").append(filter.getTable()).append(" ").append(filter.getTableAlias());

                sql.append(" where 1=1 and ").append(filter.getTableAlias() + "."+filter.getJoinKey()+"="+tableAlias+"."+filter.getKey());
                if (filter.getCompares() != null && filter.getCompares().size() > 0) {
                    for (ICompareBean item : filter.getCompares()) {
                        processCondition(filter.getTableAlias(),item, sql, values);
                    }
                }
                sql.append(") ");
            }
        }
        if (cond.getUnExistFilters() != null && cond.getUnExistFilters().size() > 0) {
            for (ConditionFilter filter : cond.getUnExistFilters()) {
                sql.append(" and not exists ( select 1 from ").append(filter.getTable()).append(" ").append(filter.getTableAlias());

                sql.append(" where 1=1 and ").append(filter.getTableAlias() + "."+filter.getJoinKey()+"="+tableAlias+"."+filter.getKey());
                if (filter.getCompares() != null && filter.getCompares().size() > 0) {
                    for (ICompareBean item : filter.getCompares()) {
                        processCondition(filter.getTableAlias(),item, sql, values);
                    }
                }
                sql.append(") ");
            }
        }
        if (cond.getCompares() != null) {
            for (ICompareBean item : cond.getCompares()) {
                processCondition(tableAlias,item, sql, values);
            }
//            sql.append(" and coalesce("+tableAlias+".dr,0)=0");
        }
    }

    public static void generateOrderSql(StringBuilder sql,String tableAlias,Condition cond){
        List<OrderBaseBean> items = cond.getOrders();
        StringBuilder sb = new StringBuilder();
        for(int i =0;i<items.size();i++){
            OrderBaseBean item = items.get(i);
            String field = tableAlias + "." + item.getField();
            if(i>0){
                sb.append(",");
            }
            switch (item.getType()){
                case ASC:{
                    sb.append(field);
                    break;
                }
                case DESC:{
                    sb.append(field+" desc");
                    break;
                }
            }
        }
        if(sb.length()>0){
            sql.append(" order by ").append(sb.toString());
        }


    }



    private static void processCondition(String tableAlias, ICompareBean item, StringBuilder sb, Map<String,Object> values) {
        Integer paramIdx = values.size()+1;
        if (item instanceof CompareBaseBean) {
            CompareBaseBean bean = (CompareBaseBean) item;
            String field = tableAlias+"."+bean.getField();
            if(bean.isLower()){
                field = "lower("+field+")";
            }
            switch (bean.getType()) {
                case EQ: {
                    sb.append(" and ").append(field).append("= #{p"+paramIdx+"}");
                    values.put("p"+paramIdx,bean.getValue());
                    break;
                }
                case NE:{
                    Object obj = bean.getValue();
                    if(obj==null){
                        sb.append(" and ").append(field).append(" is not null");
                    }else{
                        sb.append(" and (").append(field).append("<> #{p"+paramIdx+"} or ").append(field).append(" is null )");
                    }
                    values.put("p"+paramIdx,bean.getValue());
                    break;
                }
                case GE:{
                    sb.append(" and ").append(field).append(">= #{p"+paramIdx+"}");
                    values.put("p"+paramIdx,bean.getValue());
                    break;
                }
                case GT:{
                    sb.append(" and ").append(field).append("> #{p"+paramIdx+"}");
                    values.put("p"+paramIdx,bean.getValue());
                    break;
                }
                case LE:{
                    sb.append(" and ").append(field).append("<= #{p"+paramIdx+"}");
                    values.put("p"+paramIdx,bean.getValue());
                    break;
                }
                case LT:{
                    sb.append(" and ").append(field).append("< #{p"+paramIdx+"}");
                    values.put("p"+paramIdx,bean.getValue());
                    break;
                }
                case LK:{
                    sb.append(" and ").append(field).append(" like #{p"+paramIdx+"}");
                    values.put("p"+paramIdx,'%'+bean.getValue().toString()+'%');
                    break;
                }
                case LKL:{
                    sb.append(" and ").append(field).append(" like #{p"+paramIdx+"}");
                    values.put("p"+paramIdx,bean.getValue().toString()+'%');
                    break;
                }
                case LKR:{
                    sb.append(" and ").append(field).append(" like #{p"+paramIdx+"}");
                    values.put("p"+paramIdx,'%'+bean.getValue().toString());
                    break;
                }
                default: {
                    throw new BaseOrmException("错误的ConditionCompareBean类型");
                }

            }
        } else if (item instanceof CompareOrGroupBean) {
            //或区间
            CompareOrGroupBean group = (CompareOrGroupBean) item;
            Condition[] childConds = group.getConditions();
            sb.append(" and ( 1 = 2 ");
            for(Condition childCond:childConds){
                StringBuilder childWhereStr = new StringBuilder();
                for(ICompareBean child:childCond.getCompares()){
                    CommonOrmUtils.processCondition(tableAlias,child,childWhereStr,values);
                    sb.append(" or ( 1 = 1 ").append(childWhereStr.toString()).append(")");
                }
            }
            sb.append(" ) ");
        } else if (item instanceof CompareIncludeBean) {
            CompareIncludeBean bean = (CompareIncludeBean) item;
            sb.append(" and ").append(bean.getField()).append(" in (");
            for(int i=0;i<bean.getValues().length;i++){
                if(i>0){
                    sb.append(",");
                }
                sb.append("#{p"+paramIdx+"_"+i+"}");
                values.put("p"+paramIdx+"_"+i,bean.getValues()[i]);
            }
            sb.append(")");

        } else if (item instanceof CompareIsEmptyBean) {

        } else if (item instanceof CompareIsNullBean){
            CompareIsNullBean bean = (CompareIsNullBean) item;
            sb.append(" and ").append(bean.getField()).append(" is null");
        } else if (item instanceof CompareIsNotNullBean){
            CompareIsNotNullBean bean = (CompareIsNotNullBean) item;
            sb.append(" and ").append(bean.getField()).append(" is not null");
        } else if (item instanceof CompareIsZeroBean){

        } else {
            throw new BaseOrmException("错误的IConditionBean类型");
        }
    }


    public static <T extends AbstractSanBatisPO> void generateInsertSql(StringBuilder sql, Map<String,Object> values, T vo){
        String tableName = CommonOrmUtils.getTableFromClz(vo.getClass());
        List<String> fieldList = CommonOrmUtils.getTableColumnFromClz(vo.getClass());
        String fieldStr = "";
        String paramStr = "" ;
        Integer currPrarmIdx = 1;
        for (int i=0;i<fieldList.size();i++){
            String field = fieldList.get(i);
            if("ts".equals(field)){
                continue;
            }
            if(fieldStr.length()>0){
                fieldStr += ",";
            }
            fieldStr += field;
            if(paramStr.length()>0){
                paramStr += ",";
            }
            //构造batis的sql参数
            String param = "p"+(currPrarmIdx++);
            paramStr += "#{"+param+"}";
            //值
            Object value = getBeanValueToDB(vo,field);
            values.put(param,value);

        }
        //TODO 写得比较丑
        String ts = getTimeStamp();
        vo.setTs(ts);
        vo.setCreateTs(ts);
        fieldStr += ",ts,createTs";
        paramStr += ",#{ts},#{ts}";
        values.put("ts",ts);
        sql.append(" insert into ").append(tableName);
        sql.append(" (").append(fieldStr).append(")");
        sql.append(" values ");
        sql.append(" (").append(paramStr).append(")");

    }

    public static <T extends AbstractSanBatisPO> void generateInsertSqlBatch(StringBuilder head, StringBuilder body, List<Map<String,Object>> list, List<T> vos){
        AbstractSanBatisPO target = vos.get(0);
        String tableName = CommonOrmUtils.getTableFromClz(target.getClass());
        List<String> fieldList = CommonOrmUtils.getTableColumnFromClz(target.getClass());
        Map<String,String> fieldSqlMap = null;
        String fieldStr = "";
        String paramStr = "" ;
        Integer currPrarmIdx = 1;
        for(T vo:vos){
            if(fieldSqlMap==null){
                fieldSqlMap = new LinkedHashMap<>();
                for (int i=0;i<fieldList.size();i++){
                    String field = fieldList.get(i);
                    if("ts".equals(field)){
                        continue;
                    }
                    if(fieldStr.length()>0){
                        fieldStr += ",";
                    }
                    fieldStr += field;
                    if(paramStr.length()>0){
                        paramStr += ",";
                    }
                    //构造batis的sql参数
                    String param = "p"+(currPrarmIdx++);
                    paramStr += "#{item."+param+"}";
                    fieldSqlMap.put(field,param);
                }
            }
            String ts = getTimeStamp();
            Map<String,Object> row = new HashMap<>();
            for(Map.Entry<String,String> entry:fieldSqlMap.entrySet()){
                Object value = getBeanValueToDB(vo, entry.getKey());
                row.put(entry.getValue(),value);
            }
            row.put("ts",ts);
            list.add(row);
            vo.setTs(ts);
            vo.setCreateTs(ts);
        }

        fieldStr += ",ts,createTs";
        paramStr += ",#{item.ts},#{item.ts}";
        head.append(" insert into ").append(tableName);
        head.append(" (").append(fieldStr).append(")");
        head.append(" values ");
        body.append(" (").append(paramStr).append(")");

    }

    public static <T extends AbstractSanBatisPO> void generateUpdateSql(StringBuilder sql, Map<String,Object> values, Condition cond, T vo){
        if(cond.getExistFilters()!=null&&cond.getExistFilters().size()>0){
            //其实是可以，但是这么玩太危险、应用场景也不多，暂时不予支持
            throw new BaseOrmException("更新模式不支持过滤器");
        }
        if(cond.getUnExistFilters()!=null&&cond.getUnExistFilters().size()>0){
            //其实是可以，但是这么玩太危险、应用场景也不多，暂时不予支持
            throw new BaseOrmException("更新模式不支持过滤器");
        }
        String tableName = CommonOrmUtils.getTableFromClz(vo.getClass());
        List<String> fieldList = CommonOrmUtils.getTableColumnFromClz(vo.getClass());
        String fieldStr = "";
        Integer currPrarmIdx = 1;
        for (int i=0;i<fieldList.size();i++){
            String field = fieldList.get(i);
            if("ts".equals(field.toLowerCase())){
                continue;
            }
            if(fieldStr.length()>0){
                fieldStr += ",";
            }
            String param = "p"+(currPrarmIdx++);
            fieldStr = fieldStr +""+field+"=#{"+param+"}";
            //值
            Object value = getBeanValueToDB(vo,field);
            values.put(param,value);
        }
        fieldStr += ",ts=#{ts}";
        String ts = getTimeStamp();
        values.put("ts",ts);
        vo.setTs(ts);
        sql.append(" update ").append(tableName).append(" a ");
        sql.append(" set ").append(fieldStr);
        //where条件处理
        StringBuilder whereStr = new StringBuilder();
        if(cond.getCompares()!=null&&cond.getCompares().size()>0){
            for (ICompareBean item : cond.getCompares()) {
                processCondition("a",item, whereStr, values);
            }
        }else{
            throw new BaseOrmException("更新模式必须带有条件");
        }
        sql.append(" where 1 = 1 ").append(whereStr.toString());

    }

    public static <T extends AbstractSanBatisPO> void generateRemoveSql(StringBuilder sql, Map<String,Object> values, Condition cond, Class<T> clz){
        if(cond.getExistFilters()!=null&&cond.getExistFilters().size()>0){
            //其实是可以，但是这么玩太危险、应用场景也不多，暂时不予支持
            throw new BaseOrmException("更新模式不支持过滤器");
        }
        if(cond.getUnExistFilters()!=null&&cond.getUnExistFilters().size()>0){
            //其实是可以，但是这么玩太危险、应用场景也不多，暂时不予支持
            throw new BaseOrmException("更新模式不支持过滤器");
        }
        String tableName = CommonOrmUtils.getTableFromClz(clz);
        String fieldStr = "dr=1,ts=#{ts}";
        values.put("ts",getTimeStamp());
        sql.append(" update ").append(tableName).append(" a ");
        sql.append(" set ").append(fieldStr);
        //where条件处理
        StringBuilder whereStr = new StringBuilder();
        if(cond.getCompares()!=null&&cond.getCompares().size()>0){
            for (ICompareBean item : cond.getCompares()) {
                processCondition("a",item, whereStr, values);
            }
        }else{
            throw new BaseOrmException("更新模式必须带有条件");
        }
        sql.append(" where 1 = 1 ").append(whereStr.toString());

    }

    public static <T extends AbstractSanBatisPO> void generateDeleteSql(StringBuilder sql, Map<String,Object> values, Condition cond, Class<T> clz){
        if(cond.getExistFilters()!=null&&cond.getExistFilters().size()>0){
            //其实是可以，但是这么玩太危险、应用场景也不多，暂时不予支持
            throw new BaseOrmException("更新模式不支持过滤器");
        }
        if(cond.getUnExistFilters()!=null&&cond.getUnExistFilters().size()>0){
            //其实是可以，但是这么玩太危险、应用场景也不多，暂时不予支持
            throw new BaseOrmException("更新模式不支持过滤器");
        }
        String tableName = CommonOrmUtils.getTableFromClz(clz);
        sql.append(" delete from ").append(tableName).append(" a ");
        //where条件处理
        StringBuilder whereStr = new StringBuilder();
        if(cond.getCompares()!=null&&cond.getCompares().size()>0){
            for (ICompareBean item : cond.getCompares()) {
                processCondition("a",item, whereStr, values);
            }
        }else{
            throw new BaseOrmException("更新模式必须带有条件");
        }
        sql.append(" where 1 = 1 ").append(whereStr.toString());

    }

//    public static <T extends ValueObject> BaseUpdateExecutor generateRemoveByCondSql(Class<T> clz,Condition cond){
//        String tableName = CommonOrmUtils.getTableFromClz(clz);
//        List<Object> values = new ArrayList<>();
//        StringBuilder sb = new StringBuilder();
//
//        String ts=getTimeStamp();
//
//        values.add(ts);
//        sb.append(" update ").append(tableName).append(" a ");
//        sb.append(" set a.dr=1,a.ts=? ");
//        //where条件处理
//        StringBuilder whereStr = new StringBuilder();
//        if(cond.getCompares()!=null&&cond.getCompares().size()>0){
//            for (ICompareBean item : cond.getCompares()) {
//                processCondition("a",item, whereStr, values);
//            }
//        }else{
//            throw new BaseOrmException("更新模式必须带有条件");
//        }
//        sb.append(" where 1 = 1 ").append(whereStr.toString());
//
//        BaseUpdateExecutor executor = new BaseUpdateExecutor(sb.toString(),values);
//        return executor;
//    }

//    public static <T extends ValueObject> BaseUpdateExecutor generateDeleteByCondSql(Class<T> clz,Condition cond){
//        String tableName = CommonOrmUtils.getTableFromClz(clz);
//        List<Object> values = new ArrayList<>();
//        StringBuilder sb = new StringBuilder();
//
//        String ts=getTimeStamp();
//
////        values.add(ts);
//        sb.append(" delete ");
//        sb.append(" from ").append(tableName).append(" a ");
//        //where条件处理
//        StringBuilder whereStr = new StringBuilder();
//        if(cond.getCompares()!=null&&cond.getCompares().size()>0){
//            for (ICompareBean item : cond.getCompares()) {
//                processCondition("a",item, whereStr, values);
//            }
//        }else{
//            throw new BaseOrmException("更新模式必须带有条件");
//        }
//        sb.append(" where 1 = 1 ").append(whereStr.toString());
//
//        BaseUpdateExecutor executor = new BaseUpdateExecutor(sb.toString(),values);
//        return executor;
//    }

    private static Object getBeanValueToDB(AbstractSanBatisPO vo, String field) throws BaseOrmException {
        try {
            Object value=null;
            PropertyDescriptor pd = new PropertyDescriptor(field, vo.getClass());
            Method getter = pd.getReadMethod();
            if(getter!=null){
                value=getter.invoke(vo);
            }
            return value;
        }catch (Exception e){
            throw new BaseOrmException("Bean 取值错误，runtime级别");
        }

    }

    private static String getTimeStamp(){
        return TIME_STAMP_FORMAT.format(new Date());
    }
}
