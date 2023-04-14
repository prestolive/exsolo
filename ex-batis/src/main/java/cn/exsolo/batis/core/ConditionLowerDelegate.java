package cn.exsolo.batis.core;


import cn.exsolo.batis.core.condition.CompareBaseBean;
import cn.exsolo.batis.core.condition.ICompareBean;
import cn.exsolo.batis.core.ex.BaseOrmException;

/**
 *  小写处理代理类
 *  具体用法   cond.lower().lk("field_code","%key%")
 * @author ：prestolive
 * @date ：2022/1/27 11:11
 */
public class ConditionLowerDelegate {

    private Condition condition;

    public ConditionLowerDelegate(Condition condition) {
        this.condition = condition;
    }

    public Condition lk(String field, Object value) {
        this.condition.lk(field,value);
        //设置小写标志位
        setLower();
        return this.condition;
    }

    public Condition lkr(String field, Object value) {
        this.condition.lkr(field,value);
        //设置小写标志位
        setLower();
        return this.condition;
    }

    public Condition lkl(String field, Object value) {
        this.condition.lkl(field,value);
        //设置小写标志位
        setLower();
        return this.condition;
    }

    public Condition eq(String field, Object value) {
        if(value!=null&&!(value instanceof String)){
            throw new BaseOrmException("小写判断条件仅支持String类型");
        }
        this.condition.eq(field,value);
        //设置小写标志位
        setLower();
        return this.condition;
    }

    public Condition ne(String field, Object value) {
        if(value!=null&&!(value instanceof String)){
            throw new BaseOrmException("小写判断条件仅支持String类型");
        }
        this.condition.ne(field,value);
        //设置小写标志位
        setLower();
        return this.condition;
    }

    /**
     * 取出刚加进去的那个设置小写标志位
     */
    private void setLower(){
        int size =  this.condition.compares.size();
        ICompareBean bean  = this.condition.compares.get(size-1);
        if(bean instanceof CompareBaseBean){
            CompareBaseBean compareBaseBean = (CompareBaseBean) bean;
            compareBaseBean.setLower(true);
        }
    }
}
