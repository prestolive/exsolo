package cn.exsolo.batis.core;

import cn.exsolo.batis.core.condition.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by prestolive on 2018/2/2.
 */
public class Condition {

    public static String ASC = "ASC";

    public static String DESC = "DESC";

    protected List<ICompareBean> compares;

    private List<ConditionFilter> existFilters;

    private List<ConditionFilter> unExistFilters;

    private List<OrderBaseBean> orders;


    public Condition() {
        this.compares = new ArrayList<>();
        this.existFilters = new ArrayList<>();
        this.unExistFilters = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    public List<ICompareBean> getCompares() {
        return compares;
    }

    public List<ConditionFilter> getExistFilters() {
        return existFilters;
    }

    public List<ConditionFilter> getUnExistFilters() {
        return unExistFilters;
    }

    public List<OrderBaseBean> getOrders() {
        return orders;
    }

    /**
     * 返回小写处理代理类
     * @return
     */
    public ConditionLowerDelegate lower(){
        return new ConditionLowerDelegate(this);
    }

    public ConditionIfNotEmptyDelegate ifNotEmpty(){
        return new ConditionIfNotEmptyDelegate(this);
    }

    @Deprecated
    public Condition addFilter(ConditionFilter filter){
        this.existFilters.add(filter);
        return this;
    }

    public Condition exist(ConditionFilter filter){
        this.existFilters.add(filter);
        return this;
    }

    public Condition notExist(ConditionFilter filter){
        this.unExistFilters.add(filter);
        return this;
    }

    public Condition eq(String field, Object value){
        this.compares.add(new CompareBaseBean(CompareBaseBean.Type.EQ,field,value));
        return this;
    }

    public Condition ne(String field, Object value){
        this.compares.add(new CompareBaseBean(CompareBaseBean.Type.NE,field,value));
        return this;
    }

    /**
     * @desc  大于
     * @param field
     * @param value
     * @Author: Zhouu
     * @Date 2017/8/4 8:54
     * @return
     */
    public Condition gt(String field, Object value){
        this.compares.add(new CompareBaseBean(CompareBaseBean.Type.GT,field,value));
        return this;
    }

    /**
     * @desc  小于
     * @param field
     * @param value
     * @Author: Zhouu
     * @Date 2017/8/4 8:54
     * @return
     */
    public Condition lt(String field, Object value){
        this.compares.add(new CompareBaseBean(CompareBaseBean.Type.LT,field,value));
        return this;
    }

    /**
     * @desc  大于等于
     * @param field
     * @param value
     * @Author: Zhouu
     * @Date 2017/8/4 8:54
     * @return
     */
    public Condition ge(String field, Object value){
        this.compares.add(new CompareBaseBean(CompareBaseBean.Type.GE,field,value));
        return this;
    }
    /**
     * @desc  小于等于
     * @param field
     * @param value
     * @Author: Zhouu
     * @Date 2017/8/4 8:54
     * @return
     */
    public Condition le(String field, Object value){
        this.compares.add(new CompareBaseBean(CompareBaseBean.Type.LE,field,value));
        return this;
    }
    /**
     * 模糊查询匹配字符串左边部分
     */
    public Condition lkl(String field, Object value){
        this.compares.add(new CompareBaseBean(CompareBaseBean.Type.LKL,field,value));
        return this;
    }
    /**
     * 模糊查询匹配字符串右边部分
     */
    public Condition lkr(String field, Object value){
        this.compares.add(new CompareBaseBean(CompareBaseBean.Type.LKR,field,value));
        return this;
    }
    /**
     * 模糊查询匹配字符串
     */
    public Condition lk(String field, Object value){
        this.compares.add(new CompareBaseBean(CompareBaseBean.Type.LK,field,value));
        return this;
    }

    public Condition in(String field, Object[] values){
        this.compares.add(new CompareIncludeBean(field,values));
        return this;
    }

    public Condition in(String field, Collection values){
        Object[] arr = values.toArray(new Object[values.size()]);
        this.compares.add(new CompareIncludeBean(field,arr));
        return this;
    }

    public Condition or(Condition... conds){
        this.compares.add(new CompareOrGroupBean(conds));
        return this;
    }

    public Condition and(Condition... conds){
        for(Condition cond:conds){
            this.compares.addAll(cond.getCompares());
        }
        return this;
    }


    public Condition orderBy(String field){
        this.orders.add(new OrderBaseBean(OrderBaseBean.OrderType.ASC,field));
        return this;
    }

    public Condition orderBy(String field, String type){
        if(type.equals(Condition.ASC)){
            this.orders.add(new OrderBaseBean(OrderBaseBean.OrderType.ASC,field));
        }else if(type.equals(Condition.DESC)){
            this.orders.add(new OrderBaseBean(OrderBaseBean.OrderType.DESC,field));
        }
        return this;
    }

    public Condition isNotNull(String field){
        this.compares.add(new CompareIsNotNullBean(field));
        return this;
    }

    public Condition isNull(String field){
        this.compares.add(new CompareIsNullBean(field));
        return this;
    }
}
