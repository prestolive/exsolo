package cn.exsolo.batis.core;

import cn.exsolo.batis.core.ex.BaseOrmException;
import org.apache.commons.lang3.StringUtils;

/**
 *  关键字不为空才处理
 *  具体用法   cond.ifNotEmpty().lk("field_code","%key%")
 * @author ：prestolive
 * @date ：2022/1/27 11:11
 */
public class ConditionIfNotEmptyDelegate {

    private Condition condition;

    public ConditionIfNotEmptyDelegate(Condition condition) {
        this.condition = condition;
    }

    public Condition lk(String field, Object value) {
        if(value!=null&&StringUtils.isNotEmpty(value.toString())){
            this.condition.lk(field,value);
        }
        return this.condition;
    }

    public Condition lkr(String field, Object value) {
        if(value!=null&&StringUtils.isNotEmpty(value.toString())){
            this.condition.lkr(field,value);
        }
        return this.condition;
    }

    public Condition lkl(String field, Object value) {
        if(value!=null&&StringUtils.isNotEmpty(value.toString())){
            this.condition.lkl(field,value);
        }
        return this.condition;
    }

    public Condition eq(String field, Object value) {
        if(value!=null&&!(value instanceof String)){
            throw new BaseOrmException("小写判断条件仅支持String类型");
        }
        if(value!=null&&StringUtils.isNotEmpty(value.toString())){
            this.condition.eq(field,value);
        }
        return this.condition;
    }

    public Condition ne(String field, Object value) {
        if(value!=null&&!(value instanceof String)){
            throw new BaseOrmException("小写判断条件仅支持String类型");
        }

        if(value!=null&&StringUtils.isNotEmpty(value.toString())){
            this.condition.ne(field,value);
        }
        return this.condition;
    }

}
