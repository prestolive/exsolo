package cn.exsolo.batis.core;


import cn.exsolo.batis.core.condition.ICompareBean;

import java.util.List;

/**
 * 查询条件作用域处理类
 * @author ：prestolive
 * @date ：2022/1/27 11:11
 */
public class ConditionDomainDelegate {

    private Condition condition;

    private int domain = 0;

    public ConditionDomainDelegate(Condition condition,int domain) {
        this.condition = condition;
        this.domain = domain;
    }

    public List<ICompareBean> getCompares() {
//        if(domain==0){
//            return condition.compares;
//        }else if(domain==1){
//            return condition.compares_1;
//        }else if(domain==2){
//            return condition.compares_2;
//        }else if(domain==30){
//            return condition.compares_3;
//        }else{
//            return condition.compares;
//        }
        return condition.compares;
    }

//
//    public ConditionDomainDelegate eq(String field, Object value) {
//        condition.domain = this.domain;
//        condition.eq(field,value);
//        condition.domain = 0;
//        return this;
//    }
//
//    public ConditionDomainDelegate ne(String field, Object value) {
//        condition.domain = this.domain;
//        condition.ne(field,value);
//        condition.domain = 0;
//        return this;
//    }
//    public ConditionDomainDelegate gt(String field, Object value) {
//        condition.domain = this.domain;
//        condition.gt(field,value);
//        condition.domain = 0;
//        return this;
//    }
//    public ConditionDomainDelegate lt(String field, Object value) {
//        condition.domain = this.domain;
//        condition.lt(field,value);
//        condition.domain = 0;
//        return this;
//    }
//    public ConditionDomainDelegate ge(String field, Object value) {
//        condition.domain = this.domain;
//        condition.ge(field,value);
//        condition.domain = 0;
//        return this;
//    }
//    public ConditionDomainDelegate le(String field, Object value) {
//        condition.domain = this.domain;
//        condition.le(field,value);
//        condition.domain = 0;
//        return this;
//    }
//    public ConditionDomainDelegate lk(String field, Object value) {
//        condition.domain = this.domain;
//        condition.lk(field,value);
//        condition.domain = 0;
//        return this;
//    }
//    public ConditionDomainDelegate lkl(String field, Object value) {
//        condition.domain = this.domain;
//        condition.lkl(field,value);
//        condition.domain = 0;
//        return this;
//    }
//    public ConditionDomainDelegate lkr(String field, Object value) {
//        condition.domain = this.domain;
//        condition.lkr(field,value);
//        condition.domain = 0;
//        return this;
//    }
}
