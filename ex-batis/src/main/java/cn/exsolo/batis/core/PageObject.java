package cn.exsolo.batis.core;

import java.util.List;

/**
 * Created by prestolive on 2017/6/20.
 */
public class PageObject<T>  {

    public PageObject() {

    }

    private List<T> values;

    private Pagination pagination;

    public List<T> getValues() {
        return values;
    }

    public void setValues(List<T> values) {
        this.values = values;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
