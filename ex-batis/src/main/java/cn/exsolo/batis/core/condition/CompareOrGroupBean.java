package cn.exsolo.batis.core.condition;


import cn.exsolo.batis.core.Condition;
import cn.exsolo.batis.core.ex.BaseOrmException;

/**
 * 或条件分组
 * Created by prestolive on 2018/2/2.
 */
public class CompareOrGroupBean implements ICompareBean {

    private Condition[] conds;

    public CompareOrGroupBean(Condition... conds) {
        for(Condition cond:conds){
            if(cond.getExistFilters()!=null&&cond.getExistFilters().size()>0){
                throw new BaseOrmException("查询条件或区间无需过滤选项");
            }
            if(cond.getUnExistFilters()!=null&&cond.getUnExistFilters().size()>0){
                throw new BaseOrmException("查询条件或区间无需过滤选项");
            }
            if(cond.getOrders()!=null&&cond.getOrders().size()>0){
                throw new BaseOrmException("查询条件或区间无需排序选项");
            }
        }
        this.conds = conds;
    }

    public Condition[] getConditions() {
        return conds;
    }

}
